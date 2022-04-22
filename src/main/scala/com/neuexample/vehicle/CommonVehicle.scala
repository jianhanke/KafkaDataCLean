package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object CommonVehicle {


  def isDeleteBasedOnCommon(last_json: JSONObject, cur_json: JSONObject): Boolean ={

    var isDelete = false;

    val totalVoltage: Int = cur_json.getIntValue("totalVoltage")
    val soc: Int = cur_json.getIntValue("soc")
    val insulationResistance: Int = cur_json.getIntValue("insulationResistance")
    var cellVoltageArray: Array[Int] = stringToIntArray(cur_json.getString("cellVoltages"))
    var temperatureArray: Array[Int] = stringToIntArray(cur_json.getString("probeTemperatures"))

    if(last_json != null ){

      var last_soc: Int = last_json.getIntValue("soc")
      val last_insulationResistance: Int = last_json.getIntValue("insulationResistance")

      // soc清洗判断
      if(soc == 0 &&  math.abs(last_soc - soc) > 20 ){
        var dirtySocCount: Int = last_json.getIntValue("dirtySocCount")
        if(dirtySocCount < 3){
          dirtySocCount += 1;
          cur_json.put("soc", last_soc);
        }
        cur_json.put("dirtySocCount", dirtySocCount);
      }

      // insulationResistance清洗判断
      if(insulationResistance < 20000  && last_insulationResistance - insulationResistance >= 1000000  && cellVoltageArray != null &&  cellVoltageArray.min > 2500){
        var dirtyInsulationResistanceCount: Int = last_json.getIntValue("dirtyInsulationResistanceCount")
        if(dirtyInsulationResistanceCount < 3){
          dirtyInsulationResistanceCount += 1;
          cur_json.put("insulationResistance", last_insulationResistance);
        }
        cur_json.put("dirtyInsulationResistanceCount", dirtyInsulationResistanceCount);
      }
    }



    //单体电压清洗判断
    if(!isDelete &&  (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0 || cellVoltageArray.max > 4800) ){
//      if(cellVoltageArray == null){
//        println("cellvoltages is null")
//      }else{
//        println("voltage :"+cellVoltageArray.mkString(","))
//      }
      if(last_json == null){
        isDelete = true;
      }else{
        var dirtyVoltageCount: Int = last_json.getIntValue("dirtyVoltageCount")
        if(dirtyVoltageCount < 3){
          dirtyVoltageCount += 1;
          cur_json.put("cellVoltages", stringToList(last_json.getString("cellVoltages")))
        }
        cur_json.put("dirtyVoltageCount", dirtyVoltageCount);
      }
    }

    // 温度清洗判断
    if(!isDelete && ( temperatureArray == null ||  temperatureArray.min == 0 || temperatureArray.max >= 165 ) ){
      if(last_json == null){
        isDelete = true;
      }else{
        var dirtyTemperatureCount: Int = cur_json.getIntValue("dirtyTemperatureCount")
        if(dirtyTemperatureCount < 3){
          dirtyTemperatureCount += 1;
          cur_json.put("probeTemperatures", stringToList(last_json.getString("probeTemperatures")))
        }
        cur_json.put("dirtyTemperatureCount", dirtyTemperatureCount);
      }
    }


    isDelete
  }

}
