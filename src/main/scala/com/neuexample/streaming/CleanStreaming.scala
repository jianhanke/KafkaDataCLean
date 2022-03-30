package com.neuexample.streaming

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.streaming.{State, StateSpec}
import org.apache.spark.streaming.dstream.{DStream, MapWithStateDStream}
import com.neuexample.vehicle.Sgmw._
import com.neuexample.vehicle.Jh._
import com.neuexample.vehicle.Geely._
import com.neuexample.utils.CommonFuncs._

/**
  * 清洗规则细节：
  *
  *   当前数据某一字段有问题，则 当前数据字段 = 前一刻数据字段 ，如没有前一刻数据，则这一整条脏数据删除掉。
  *   如当前数据必须要和前一刻做判断，但没有前一刻数据，则这条（干净或脏）整个数据会保留下来。
  *   当前时刻 与 上一时刻做判断，由于网络延迟、数据进入快慢问题,可能将 下一时刻当成上一时刻做判断
  *   前一时刻的内存保留时间很长，可能会出现当前时刻与上一时刻数据时间跨度较大
  */

object CleanStreaming extends Serializable {


  def vehicleClean(vehicleDStream: DStream[String]): DStream[String]={

     vehicleDStream.map {
      line => {
        val json: JSONObject = JSON.parseObject(line)
        (json.getString("vin"), json)
      }
    }.mapWithState(StateSpec.function(func_state_c))

  }


  val func_state_c = ( key: String,values: Option[JSONObject],state: State[JSONObject] )=> {

    val old_obj: JSONObject = state.getOption().getOrElse(null)  // 获取同一车辆的上一条数据
    val new_obj: JSONObject = values.get

    var isRetain = true;
    val vehicleFactory: Integer = new_obj.getInteger("vehicleFactory")
    val year: Integer = new_obj.getInteger("year")

    if(year != null && year == 22  ){    // 过滤超出年份的数据

    }else{
      isRetain = false;
    }

    if(isRetain){
      if(vehicleFactory == 1) {
        isRetain = isRetainGgmw(old_obj, new_obj)
      }else if(vehicleFactory == 5){          // 将
        isRetain = isRetainGeely(old_obj, new_obj);
      }
    }

    if(isRetain){  // 保留下来的
      val json: JSONObject = JSON.parseObject(new_obj.toString)    // 必须转化成新的

      if(json.getInteger("vehicleFactory") == 2){
        processJhData(json)
      }
      cleanArrayValue(json)

      state.update(json);

      json.toString
    }else{
      null
    }

  }

  def cleanArrayValue(json: JSONObject){
    val cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    val probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))

    if(cellVoltageArray != null){
      json.put("batteryMaxVoltage", cellVoltageArray.max)
      json.put("batteryMinVoltage", cellVoltageArray.min)
      json.put("totalVoltage", cellVoltageArray.sum);
      json.put("voltage", cellVoltageArray.sum);
      json.put("cellCount",cellVoltageArray.length);

      json.put("maxVoltagebatteryNum", cellVoltageArray.indexOf(cellVoltageArray.max) + 1)
      json.put("minVoltagebatteryNum", cellVoltageArray.indexOf(cellVoltageArray.min) + 1)
    }

    if(probeTeptureArray !=null ){
      json.put("maxTemperature", probeTeptureArray.max - 40);
      json.put("minTemperature", probeTeptureArray.min - 40);
      json.put("temperatureProbeCount",probeTeptureArray.length);
      json.put("temperature", probeTeptureArray.sum / probeTeptureArray.length - 40 );

      json.put("maxTemperatureNum", probeTeptureArray.indexOf(probeTeptureArray.max) + 1 )
      json.put("minTemperatureNum", probeTeptureArray.indexOf(probeTeptureArray.min) + 1 )
    }


  }

}
