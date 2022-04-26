package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs.stringToIntArray

object Chery {

  def processChery(json: JSONObject): Unit ={
    var probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    if(probeTeptureArray !=null && probeTeptureArray.length >28){
      probeTeptureArray = probeTeptureArray.slice(0,28)
    }
  }
}
