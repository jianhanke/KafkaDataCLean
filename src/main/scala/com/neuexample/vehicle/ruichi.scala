package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs.stringToIntArray

object ruichi {
  /**
   * 处理瑞驰数据清洗
   * @param json
   */
  def processRuiChiData(json:JSONObject){
    val cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    val probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    val totalCurrent:Integer = json.getInteger("totalCurrent")
    if(cellVoltageArray !=null && cellVoltageArray.nonEmpty) {
      json.put("minVoltagebatteryNum", cellVoltageArray.zipWithIndex.min._2+1)
      json.put("maxVoltagebatteryNum", cellVoltageArray.zipWithIndex.max._2+1)
    }
    if(probeTeptureArray !=null && probeTeptureArray.nonEmpty)
    {
      json.put("minTemperatureNum", probeTeptureArray.zipWithIndex.min._2+1)
      json.put("maxTemperatureNum", probeTeptureArray.zipWithIndex.max._2+1)
    }

    json.put("maxVoltageSystemNum",1)//默认值1
    json.put("minVoltageSystemNum",1)//默认值1
    json.put("maxTemperatureSystemNum",1)//默认值1
    json.put("minTemperatureSystemNum",1)//默认值1
    json.put("current",totalCurrent*100 - 1000000) //换算成真实电流值
  }
}
