package com.neuexample.vehicle

import com.alibaba.fastjson.{JSON, JSONObject}
import com.neuexample.utils.CommonFuncs._

object Jh {



  /**
    * 江淮数据单体序号 温度序号 充电状态 总电流预处理
    * @param json
    * @return
    */
  def processJhData(json:JSONObject){
    val cellVoltageArray: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    val probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    val totalCurrent:Integer = json.getInteger("totalCurrent")
    if(cellVoltageArray !=null) {
      json.put("minVoltagebatteryNum", cellVoltageArray.zipWithIndex.min._2+1)
      json.put("maxVoltagebatteryNum", cellVoltageArray.zipWithIndex.max._2+1)
    }
    if(probeTeptureArray !=null)
    {
      json.put("minTemperatureNum", probeTeptureArray.zipWithIndex.min._2+1)
      json.put("maxTemperatureNum", probeTeptureArray.zipWithIndex.max._2+1)
    }

    json.put("maxVoltageSystemNum",1)//默认值1
    json.put("minVoltageSystemNum",1)//默认值1
    json.put("maxTemperatureSystemNum",1)//默认值1
    json.put("minTemperatureSystemNum",1)//默认值1
    json.put("current",(totalCurrent*100 - 1000000)) //换算成真实电流值
    //处理充电状态解析
    val customField = json.getString("customField")
    val customFieldJsonObj:JSONObject= JSON.parseObject(customField)
    val chargePlugStatus:Int = customFieldJsonObj.getInteger("chargePlugStatus")
    val chargeStatus = if(chargePlugStatus==3) 1 else 3
    json.put("chargeStatus",chargeStatus)

  }

}
