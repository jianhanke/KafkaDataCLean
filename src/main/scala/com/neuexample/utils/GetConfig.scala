package com.neuexample.utils
import java.io.FileInputStream
import java.util.Properties


object GetConfig extends Serializable {


  def getProperties(filename :String)={
    var properties = new Properties()
    properties.load(new FileInputStream(filename))
    properties
  }


}
