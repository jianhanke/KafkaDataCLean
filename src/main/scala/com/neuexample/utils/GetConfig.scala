package com.neuexample.utils
import java.io.FileInputStream
import java.sql.{Connection, DriverManager}
import java.util.Properties


object GetConfig extends Serializable {


  def getProperties(filename :String)={
    var properties = new Properties()
    properties.load(new FileInputStream(filename))
    properties
  }

  //创建mysql连接
  def getMysqlConn(properties :Properties) :Connection={
    Class.forName("com.mysql.cj.jdbc.Driver")
    //获取mysql连接
    val conn: Connection = DriverManager.getConnection(properties.getProperty("mysql.conn"), properties.getProperty("mysql.user"), properties.getProperty("mysql.passwd"))
    conn
  }

}
