package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs.{stringToIntArray, stringToList}

object Chery {

  /**
   *
   * @param json
   */
  def processCheryDataDJ1902(json: JSONObject): Unit ={
    //总电流数据处理totalCurrent->current
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    if(probeTeptureArray !=null && probeTeptureArray.length >28){
      probeTeptureArray = probeTeptureArray.slice(0,28)
      json.put("probeTemperatures",probeTeptureArray.mkString("[",",","]"))
    }

  }

  def processCheryDataDJ2015(json: JSONObject): Unit ={
    //总电流数据处理totalCurrent->current
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    if(probeTeptureArray !=null && probeTeptureArray.length >21){
      probeTeptureArray = probeTeptureArray.slice(0,21)
      json.put("probeTemperatures",probeTeptureArray.mkString("[",",","]"))
    }

  }
}
