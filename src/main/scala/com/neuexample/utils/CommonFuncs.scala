package com.neuexample.utils

import java.text.SimpleDateFormat

object CommonFuncs {


  /**
    *
    * @param str  "1","2","3"
    * @return      Array[1,2,3]
    */
  def stringToIntArray(str:String):Array[Int]={

    if(str != null && str.length > 2) {
      val strArr: Array[String] = str.substring(1, str.length - 1).split(",")
      val intArr = new Array[Int](strArr.length);
      for (i <- 0 until intArr.length) {
        intArr(i) = strArr(i).toInt
      }
      intArr
    }else{
      null
    }
  }


  /**
    *
    * @param str  "[1,2,3]"
    * @return      [1,2,3]
    */
  def stringToList(str:String): Array[Int]={


    if(str == null || str.equals("[]") ) {
      Array()
    }else {
      val strArray: Array[String] = str.substring(1, str.length - 1).split(",")
      val intArray: Array[Int] =new Array[Int](strArray.length);
      for (i <- 0 until strArray.length) {
        intArray(i) = strArray(i).toInt
      }
      intArray
    }

  }


  def mkctime (year:Int,month:Int,day:Int,hours:Int,minutes:Int,seconds:Int) :Long ={

    try {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("20%02d-%02d-%02d %02d:%02d:%02d".format(year, month, day, hours, minutes, seconds)).getTime / 1000
    }catch {
      case ex: Exception=>{
        return 0;
      }
    }

  }

}
