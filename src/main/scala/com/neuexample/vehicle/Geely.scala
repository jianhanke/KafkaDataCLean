package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object Geely {

  def isDeleteBasedOnGeely(last_json: JSONObject, cur_json: JSONObject): Boolean ={

    var isDelete = false;
    var isReplaceTemperature = false;

    val totalVoltage: Int = cur_json.getIntValue("totalVoltage")
    val soc: Int = cur_json.getIntValue("soc")
    val insulationResistance: Int = cur_json.getIntValue("insulationResistance")
    var cellVoltageArray: Array[Int] = stringToIntArray(cur_json.getString("cellVoltages"))
    var temperatureArray: Array[Int] = stringToIntArray(cur_json.getString("probeTemperatures"))


    if( last_json != null ){
      var last_soc: Int = last_json.getIntValue("soc")
      val last_insulationResistance: Int = last_json.getIntValue("insulationResistance")
      var last_temperatureArray: Array[Int] = stringToIntArray(last_json.getString("probeTemperatures"))
      var last_cellVoltageArray: Array[Int] = stringToIntArray(last_json.getString("cellVoltages"))

      //soc跳变清洗判断
      if( last_soc - soc >= 20  &&  last_temperatureArray.max - temperatureArray.max >= 15 &&  last_temperatureArray.min - temperatureArray.min >= 15 &&
        last_cellVoltageArray.max - cellVoltageArray.max >= 200 && last_cellVoltageArray.min - cellVoltageArray.min >= 200 && last_cellVoltageArray.sum - cellVoltageArray.sum >= 20000 ){
        cur_json.put("year", last_json.getIntValue("year"))
        cur_json.put("month", last_json.getIntValue("month"))
        cur_json.put("day", last_json.getIntValue("day"))
        cur_json.put("hours", last_json.getIntValue("hours"))
        cur_json.put("minutes", last_json.getIntValue("minutes"))
        cur_json.put("seconds", last_json.getIntValue("seconds"))
      }

      //soc清洗判断
      if(soc == 0 &&  math.abs(last_soc - soc) > 20 ){
        var dirtySocCount: Int = last_json.getIntValue("dirtySocCount")
        if(dirtySocCount < 3){
          dirtySocCount += 1;
          cur_json.put("soc", last_soc);
        }
        cur_json.put("dirtySocCount", dirtySocCount);
      }

      //绝缘阻值清洗判断
      if(insulationResistance < 20000  && last_insulationResistance - insulationResistance >= 1000000  && cellVoltageArray != null &&  cellVoltageArray.min > 2500){
        var dirtyInsulationResistanceCount: Int = last_json.getIntValue("dirtyInsulationResistanceCount")
        if(dirtyInsulationResistanceCount < 3){
          dirtyInsulationResistanceCount += 1;
          cur_json.put("insulationResistance", last_insulationResistance);
        }
        cur_json.put("dirtyInsulationResistanceCount", dirtyInsulationResistanceCount);
      }

      // 吉利高温清洗判断
      if( temperatureArray != null && last_temperatureArray != null && temperatureArray.max == 127 && last_temperatureArray.max < 97 &&  ( insulationResistance / cellVoltageArray.sum >= 500000 || cellVoltageArray.max - cellVoltageArray.min <= 400) ){
        var dirtyHighTemperatureCount: Int = cur_json.getIntValue("dirtyHighTemperatureCount")
        if(dirtyHighTemperatureCount < 3){
          dirtyHighTemperatureCount += 1;
          isReplaceTemperature = true;
        }
        cur_json.put("dirtyTemperatureCount", dirtyHighTemperatureCount);
      }

    }


    // 单体电压清洗判断
    if(!isDelete &&  (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0 || cellVoltageArray.max > 4800) ){
      if(last_json == null){
        isDelete = true;
      }else{
        var dirtyVoltageCount: Int = last_json.getIntValue("dirtyVoltageCount")
        if(dirtyVoltageCount < 3){
          cur_json.put("cellVoltages", stringToList(last_json.getString("cellVoltages")))
          dirtyVoltageCount += 1;
        }
        cur_json.put("dirtyVoltageCount", dirtyVoltageCount);
      }
    }


    // 温度清洗判断
    if(!isDelete && !isReplaceTemperature && ( temperatureArray == null ||  temperatureArray.min == 0 || temperatureArray.max >= 165 ) ){
//      if(temperatureArray == null){
//        println("temperature is null")
//      }else{
//        println("temper:"+temperatureArray.mkString(","))
//      }
      if(last_json == null){
        isDelete = true;
      }else{
        var dirtyTemperatureCount: Int = cur_json.getIntValue("dirtyTemperatureCount")
        if(dirtyTemperatureCount < 3){
          dirtyTemperatureCount += 1;
        }
        cur_json.put("dirtyTemperatureCount", dirtyTemperatureCount);
      }
    }



    if(!isDelete && isReplaceTemperature ){
      cur_json.put("probeTemperatures", stringToList(last_json.getString("probeTemperatures")))
    }
    isDelete

  }

}
