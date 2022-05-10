package com.neuexample.vehicle

import com.alibaba.fastjson.{JSON, JSONObject}
import com.neuexample.utils.CommonFuncs.{stringToIntArray, stringToList}

object Chery {

  /**
   *
   * @param json
   */
  def processCheryDataDJ1902(json: JSONObject): Unit = {
    //总电流数据处理totalCurrent->current
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    var cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    if (probeTeptureArray != null && probeTeptureArray.length > 28) {
      probeTeptureArray = probeTeptureArray.slice(0 , 28)
      json.put("probeTemperatures", JSON.toJSON(probeTeptureArray))
    }
    if (cellVoltageArray != null && cellVoltageArray.length > 106) {
      cellVoltageArray = cellVoltageArray.slice(0 , 106)
      json.put("cellVoltageArray", JSON.toJSON(cellVoltageArray))
    }
  }

  def processCheryDataDJ2015(json: JSONObject): Unit = {
    //总电流数据处理totalCurrent->current
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    var cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    if (probeTeptureArray != null && probeTeptureArray.length > 21) {
      probeTeptureArray = probeTeptureArray.slice(0 , 21)
      json.put("probeTemperatures", JSON.toJSON(probeTeptureArray))
    }
    if (cellVoltageArray != null && cellVoltageArray.length > 108) {
      cellVoltageArray = cellVoltageArray.slice(0 , 108)
      json.put("cellVoltageArray", JSON.toJSON(cellVoltageArray))
    }
  }

  /**
   * 对于单体数组大于100 和温度数组大于34的数据进行过滤
   * @param json
   */
  def processCheryDataDJ1811A(json: JSONObject): Unit = {
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    var cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    if (probeTeptureArray != null && probeTeptureArray.length > 34) {
      probeTeptureArray = probeTeptureArray.slice(0 , 34)
      json.put("probeTemperatures", JSON.toJSON(probeTeptureArray))
    }
    if (cellVoltageArray != null && cellVoltageArray.length > 100) {
      cellVoltageArray = cellVoltageArray.slice(0 , 100)
      json.put("cellVoltageArray", JSON.toJSON(cellVoltageArray))
    }
  }

  /**
   * 对于单体数组大于100 和温度数组大于34的数据进行过滤
   * @param json
   */
  def processCheryDataDJ1903(json: JSONObject): Unit = {
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    var cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    if (probeTeptureArray != null && probeTeptureArray.length > 34) {
      probeTeptureArray = probeTeptureArray.slice(0 , 34)
      json.put("probeTemperatures", JSON.toJSON(probeTeptureArray))
    }
    if (cellVoltageArray != null && cellVoltageArray.length > 100) {
      cellVoltageArray = cellVoltageArray.slice(0 , 100)
      json.put("cellVoltageArray", JSON.toJSON(cellVoltageArray))
    }
  }

}
