package com.neuexample.vehicle

import com.alibaba.fastjson.{JSON, JSONObject}
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

  def isRetainGeely(old_obj: JSONObject, new_obj: JSONObject):Boolean={

    var isReatain = true              // 清洗保留标志符
    var isChangeTemperature = false
    var isChangeVoltage = false
    var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
    var probeTeptureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))
    val insulationResistance: Integer = new_obj.getInteger("insulationResistance")

    // 对 温度为空或，做清洗。如果此车辆有上条数据，则将上条数据 拿过来使用，否则过滤这条脏数据
    if(probeTeptureArray == null ){
      if(old_obj != null){
        isChangeTemperature = true
      }else{
        isReatain = false
      }
    }

    if( isReatain &&  (cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0) ) {
      if(old_obj != null){
        isChangeVoltage = true
      }else{
        isReatain = false
      }
    }

    if( isReatain && old_obj != null && !isChangeTemperature){
      val last_probeTemperatures: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))

      if( last_probeTemperatures != null   ){
        if( cellVoltageArray !=null && insulationResistance != null && (probeTeptureArray.max == 127 || math.abs(probeTeptureArray.max - last_probeTemperatures.max) >= 15) && !(cellVoltageArray.max - cellVoltageArray.min <= 400 || insulationResistance / (cellVoltageArray.sum / 1000.0) >= 500) ){
          isChangeTemperature = true
        }else if(probeTeptureArray.max == 0 &&  math.abs(probeTeptureArray.max - last_probeTemperatures.max) > 10){
          isChangeTemperature = true
        }else if(  math.abs(probeTeptureArray.min - last_probeTemperatures.min) > 20){
          isChangeTemperature = true
        }
      }

    }

    if(isChangeTemperature){
      new_obj.put("probeTemperatures",stringToList(old_obj.getString("probeTemperatures")))
    }
    if(isChangeVoltage){
      new_obj.put("cellVoltages",stringToList(old_obj.getString("cellVoltages")));
    }
    //将绝缘阻值单位统一由欧改为千欧2022.05.16
    if(insulationResistance !=null){
      new_obj.put("insulationResistance",insulationResistance.toInt/1000)
    }

    isReatain

  }

  def  GeelySocJump(old_obj: JSONObject, new_obj: JSONObject):JSONObject={

    val soc_old = old_obj.getInteger("soc")
    val soc_new  = new_obj.getInteger("soc")
    val maxTemp_old = old_obj.getInteger("maxTemperature")
    val maxTemp_new = new_obj.getInteger("maxTemperature")
    val minTemp_old = old_obj.getInteger("minTemperature")
    val minTemp_new = new_obj.getInteger("minTemperature")
    val maxVol_old = old_obj.getInteger("batteryMaxVoltage")
    val maxVol_new = new_obj.getInteger("batteryMaxVoltage")
    val minVol_old = old_obj.getInteger("batteryMinVoltage")
    val minVol_new = new_obj.getInteger("batteryMinVoltage")

    val time_old = mkctime(old_obj.getIntValue("year"),
      old_obj.getIntValue("month"),
      old_obj.getIntValue("day"),
      old_obj.getIntValue("hours"),
      old_obj.getIntValue("minutes"),
      old_obj.getIntValue("seconds"))

    val time_new = mkctime(new_obj.getIntValue("year"),
      new_obj.getIntValue("month"),
      new_obj.getIntValue("day"),
      new_obj.getIntValue("hours"),
      new_obj.getIntValue("minutes"),
      new_obj.getIntValue("seconds"))

    var totalVoltage_old =0
    var totalVoltage_new =0
    val cellVoltageArray_old: Array[Int] = stringToIntArray(old_obj.getString("cellVoltages"))
    val cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))

    if(cellVoltageArray_old !=null && cellVoltageArray_old.nonEmpty){
      totalVoltage_old = cellVoltageArray_old.sum
    }
    if(cellVoltageArray!=null && cellVoltageArray.nonEmpty){
      totalVoltage_new = cellVoltageArray.sum
    }
    //针对SOC跳变数据源清洗
    if(math.abs(soc_old -soc_new)>=20 &&
      math.abs(maxTemp_old-maxTemp_new)>=10 &&
      math.abs(minTemp_old-minTemp_new)>=10 &&
      math.abs(maxVol_old-maxVol_new)>=200 &&
      math.abs(minVol_old-minVol_new)>=200 &&
      math.abs(totalVoltage_old-totalVoltage_new)>=20000 &&
      math.abs(time_old-time_new)>0 &&
      math.abs(time_old-time_new)<=30){
      old_obj
    }else{
     new_obj
    }
  }

}
