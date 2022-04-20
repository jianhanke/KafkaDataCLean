package com.neuexample.vehicle

import com.alibaba.fastjson.JSONObject
import com.neuexample.utils.CommonFuncs._

object Sgmw {

  def isDeleteBasedOnSgmw(last_json: JSONObject, cur_json: JSONObject): Boolean ={
    /*
    // 总电压清洗判断
    if(cellVoltageArray != null   && cellVoltageArray.min != 0  && totalVoltage  >= cellVoltageArray.sum * 0.0011 + 10  ){
      println(totalVoltage+","+cellVoltageArray.sum * 0.0011 + 10)
      if(last_json == null){
        isDelete = true;
      }else{
        isReplaceVoltages = true;
      }
    }
    */
    false
  }

}
