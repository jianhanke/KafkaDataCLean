package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object Geely {

  /**
        对吉利车厂单独做清洗规则
    */
  def isRetainGeely(old_obj: JSONObject, new_obj: JSONObject): Boolean ={

    var isReatain = true;                 // 清洗保留标志符
    var isChangeTemperature = false;
    var isChangeVoltage = false;

    var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
    var probeTeptureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))
    val insulationResistance: Integer = new_obj.getInteger("insulationResistance")

    // 对 温度为空或，做清洗。如果此车辆有上条数据，则将上条数据 拿过来使用，否则过滤这条脏数据
    if(probeTeptureArray == null ){
      if(old_obj != null){
        isChangeTemperature = true;
      }else{
        isReatain = false;
      }
    }

    if( isReatain &&  (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0) ) {
      if(old_obj != null){
        isChangeVoltage = true
      }else{
        isReatain = false;
      }
    }

    if( isReatain && old_obj != null && !isChangeTemperature){
      val last_probeTemperatures: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))

      if( last_probeTemperatures != null   ){
        if( cellVoltageArray !=null && insulationResistance != null && (probeTeptureArray.max == 127 || math.abs(probeTeptureArray.max - last_probeTemperatures.max) >= 15) && !(cellVoltageArray.max - cellVoltageArray.min <= 400 || insulationResistance / (cellVoltageArray.sum / 1000.0) >= 500) ){
          isChangeTemperature = true;
        }else if(probeTeptureArray.max == 0 &&  math.abs(probeTeptureArray.max - last_probeTemperatures.max) > 10){
          isChangeTemperature = true;
        }else if(  math.abs(probeTeptureArray.min - last_probeTemperatures.min) > 20){
          isChangeTemperature = true;
        }
      }

    }

    if(isChangeTemperature){
      new_obj.put("probeTemperatures",stringToList(old_obj.getString("probeTemperatures")))
    }
    if(isChangeVoltage){
      new_obj.put("cellVoltages",stringToList(old_obj.getString("cellVoltages")));
    }

    isReatain
  }

}
