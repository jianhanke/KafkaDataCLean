package com.neuexample.streaming

import java.text.SimpleDateFormat

import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.streaming.{State, StateSpec}
import org.apache.spark.streaming.dstream.{DStream, MapWithStateDStream}

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



    val vin2Json: DStream[(String, JSONObject)] = vehicleDStream.map {
      line => {
        val json: JSONObject = JSON.parseObject(line,Feature.OrderedField)
        val vin: String = json.getString("vin")
//        println(line)

//        val timeStamp: Long = mkctime(json.getInteger("year")
//          , json.getInteger("month")
//          , json.getInteger("day")
//          , json.getInteger("hours")
//          , json.getInteger("minutes")
//          , json.getInteger("seconds"))
//            json.put("timeStamp", timeStamp);

//          println("chu:"+line);

        (vin, json)
      }
    }

    val judgeLastDStream: MapWithStateDStream[String, JSONObject, JSONObject, JSONObject] = vin2Json.mapWithState(StateSpec.function(func_state_c))

    val value: DStream[String] = judgeLastDStream.filter(_  != null)
      .map {
        json => {
          val cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
          val probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))

          json.put("batteryMaxVoltage", cellVoltageArray.max)
          json.put("batteryMinVoltage", cellVoltageArray.min)
          json.put("totalVoltage", cellVoltageArray.sum);
          json.put("voltage", cellVoltageArray.sum);
          json.put("cellCount",cellVoltageArray.length);

          json.put("maxTemperature", probeTeptureArray.max - 40);
          json.put("minTemperature", probeTeptureArray.min - 40);
          json.put("temperatureProbeCount",probeTeptureArray.length);
          json.put("temperature", probeTeptureArray.sum/probeTeptureArray.length - 40 );

          json.toString
        }
      }

    value
  }

  val func_state_c=( key:String,values:Option[JSONObject],state:State[JSONObject] )=> {

    val old_obj: JSONObject = state.getOption().getOrElse(null)
    val new_obj: JSONObject = values.get

//    if(old_obj!=null) {
//      println("curTime:" + new_obj.getInteger("timeStamp") + ",lastTime:" + old_obj.getInteger("timeStamp") + ",diff:" + (
//        new_obj.getInteger("timeStamp") - old_obj.getInteger("timeStamp")
//        ))
//    }

    var isContainer=true;
    val vehicleFactory: Integer = new_obj.getInteger("vehicleFactory")
    val year: Integer = new_obj.getInteger("year")

    if(year==null || year!=21 ){
      isContainer=false;
    }

    if(isContainer){
      if(vehicleFactory==1) {
        isContainer = isCleanGgmw(old_obj, new_obj)
      }else if(vehicleFactory==5){
        isContainer=  isCleanGeely(old_obj,new_obj);
      }
    }

    if(isContainer){  // 保留下来的

      val json: JSONObject = JSON.parseObject(new_obj.toString)    // 必须转化成新的
      state.update(json);
      json
    }else{
      null
    }

  }


  def isCleanGeely(old_obj: JSONObject, new_obj: JSONObject): Boolean ={

    var isContainer = true;
    var isChangeTemperature = false;
    var isChangeVoltage = false;

    var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
    var probeTeptureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))
    val insulationResistance: Integer = new_obj.getInteger("insulationResistance")

    if(probeTeptureArray == null  || probeTeptureArray.max == 0 ){
      if(old_obj != null){
        isChangeTemperature = true;
      }else{
        isContainer = false;
      }
    }

    if( isContainer &&  (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0) ) {
      if(old_obj != null){
        isChangeVoltage = true
      }else{
        isContainer = false;
      }
    }

    if( isContainer && old_obj != null ){
      val last_probeTemperatures: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))
      if( last_probeTemperatures != null && ( (probeTeptureArray.max == 127 || math.abs(probeTeptureArray.max - last_probeTemperatures.max) >= 15) && !(cellVoltageArray.max - cellVoltageArray.min >= 400 || (insulationResistance != null && insulationResistance / (cellVoltageArray.sum / 1000.0) <= 500) ) ) ){
        isChangeTemperature = true;
      }
    }

    if(isChangeTemperature){
      new_obj.put("probeTemperatures",stringToList(old_obj.getString("probeTemperatures")))
    }
    if(isChangeVoltage){
      new_obj.put("cellVoltages",stringToList(old_obj.getString("cellVoltages")));
    }

    isContainer
  }

  def isCleanGgmw(old_obj: JSONObject, new_obj: JSONObject): Boolean ={

      var isContainer = true;
      var isChangeTemperature = false;
      var isChangeVoltage = false;

      var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
      var temperatureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))
      val insulationResistance: Integer = new_obj.getInteger("insulationResistance")
      val soc: Integer = new_obj.getInteger("soc")

    if( temperatureArray == null  || temperatureArray.max == 0 || temperatureArray.max > 240 ){
      if(old_obj != null){
        isChangeTemperature = true;
      }else{
        isContainer = false;
      }
    }


    if(  isContainer && (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0  ||  cellVoltageArray.max > 4000)  ){
      if(old_obj != null){
        isChangeVoltage = true;
      }else{
        isContainer = false;
      }
    }


    if(isContainer  && insulationResistance!=null && cellVoltageArray!=null && insulationResistance < 40000 &&  (cellVoltageArray.min > 2500 || cellVoltageArray.max - cellVoltageArray.min < 800  ) ){
      if(old_obj!=null){
        new_obj.put("insulationResistance", old_obj.getInteger("insulationResistance"))
      }else{
        isContainer=false;
      }
    }

    if (isContainer  &&  old_obj != null  ) {
      val last_temperatureArray: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))
      val last_soc: Integer = old_obj.getInteger("soc")

      if(last_soc != null && soc != null && soc == 0 && math.abs(last_soc - soc) > 10){
        new_obj.put("soc", last_soc);
      }
      if(temperatureArray!=null && last_temperatureArray!=null && math.abs(last_temperatureArray.max-temperatureArray.max) > 15){
        isChangeTemperature=true;
      }
    }

    if(isChangeTemperature){
      new_obj.put("probeTemperatures",stringToList(old_obj.getString("probeTemperatures")))
    }
    if(isChangeVoltage){
      new_obj.put("cellVoltages",stringToList(old_obj.getString("cellVoltages")));
    }

      isContainer;
  }


  /**
    *
    * @param str  ["1","2","3"]
    * @return      Array[1,2,3]
    */
  def stringToIntArray(str:String):Array[Int]={


    // println(str)
    if(str!=null && str.length>2) {
        val strArr: Array[String] = str.substring(1, str.length - 1).split(",")
        val intArr = new Array[Int](strArr.length);
        for (i <- 0 until intArr.length) {
          intArr(i) = strArr(i).toInt
        }
        intArr
    }else{
      null
    }

  }


  /**
    *
    * @param str  "[1,2,3]"
    * @return      [1,2,3]
    */
  def stringToList(str:String): Array[Int]={


    if(str==null || str.equals("[]") ) {
      Array()
    }else {
      val strArray: Array[String] = str.substring(1, str.length - 1).split(",")
      val intArray: Array[Int] =new Array[Int](strArray.length);
      for (i <- 0 until strArray.length) {
        intArray(i) = strArray(i).toInt
      }
      intArray
    }

  }


  def mkctime (year:Int,month:Int,day:Int,hours:Int,minutes:Int,seconds:Int) :Long ={

    try {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("20%s-%02d-%02d %02d:%02d:%02d".format(year, month, day, hours, minutes, seconds)).getTime / 1000
    }catch {
      case e=> return 0;
    }
  }


}
