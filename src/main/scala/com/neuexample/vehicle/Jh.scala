package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object Jh {


  def isCleanJh(old_obj: JSONObject, new_obj: JSONObject): Boolean ={

    var isContainer = true;                 // 清洗保留标志符
    var isChangeVoltage = false;
    var isChangeTemperature = false;

    var cellVoltageArray: Array[Int] = stringToIntArray(new_obj.getString("cellVoltages"))
    var probeTeptureArray: Array[Int] = stringToIntArray(new_obj.getString("probeTemperatures"))

    if(cellVoltageArray == null || cellVoltageArray.min == 0 || cellVoltageArray.max == 0){
      if(old_obj != null){
        isChangeVoltage = true
      }else{
        isContainer = false;
      }
    }


    if( isContainer && old_obj != null ){

      val last_probeTemperatures: Array[Int] = stringToIntArray(old_obj.getString("probeTemperatures"))

      if(last_probeTemperatures != null && probeTeptureArray != null  ){
        if(probeTeptureArray.max == 0 &&  math.abs(probeTeptureArray.max - last_probeTemperatures.max) > 10){
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
    isContainer
  }

}
