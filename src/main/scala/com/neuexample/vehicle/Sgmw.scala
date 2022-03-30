package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object Sgmw {

  def isCleanGgmw(old_obj: JSONObject, new_obj: JSONObject): Boolean ={

    var isContainer = true;
    var isChangeTemperature = false;
    var isChangeVoltage = false;

    var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
    var temperatureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))
    val insulationResistance: Integer = new_obj.getInteger("insulationResistance")
    val soc: Integer = new_obj.getInteger("soc")

    if( temperatureArray == null  || temperatureArray.max > 240 ){
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


    if(isContainer  && insulationResistance != null && cellVoltageArray != null && insulationResistance < 40000 &&  (cellVoltageArray.min > 2500 || cellVoltageArray.max - cellVoltageArray.min < 800  ) ){
      if(old_obj != null){
        new_obj.put("insulationResistance", old_obj.getInteger("insulationResistance"))
      }else{
        isContainer = false;
      }
    }

    if (isContainer  &&  old_obj != null  ) {
      val last_temperatureArray: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))
      val last_soc: Integer = old_obj.getInteger("soc")

      if(last_soc != null && soc != null && soc == 0 && math.abs(last_soc - soc) > 10){
        new_obj.put("soc", last_soc);
      }
      if(temperatureArray != null && last_temperatureArray != null){

        if(math.abs(last_temperatureArray.max - temperatureArray.max) > 15){
          isChangeTemperature = true;
        }else if(temperatureArray.max == 0  && math.abs(last_temperatureArray.max - temperatureArray.max) > 10 ){
          isChangeTemperature = true;
        }else if(math.abs(last_temperatureArray.min - temperatureArray.min) > 20 ){
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
    isContainer;
  }

}
