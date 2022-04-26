package com.neuexample.utils

import com.neuexample.utils.GetConfig.getMysqlConn
import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast

import scala.collection.mutable.HashSet

object CheryVin {
  @volatile private var vinSet: HashSet[String] = _

  def creatInstance():HashSet[String] = {
    if (vinSet == null) {
      synchronized {
        if (vinSet == null) {
          println("-----GetCheryVinList-----")
          vinSet = getVinData
        }
      }
    }
    vinSet
  }

  private def getVinData(): HashSet[String] = {
    val properties = GetConfig.getProperties("test.properties")
    val conn = getMysqlConn(properties)
    val vinSet:HashSet[String] =HashSet[String]()
    val Select_Sql = "select vin from battery.GX_Vin where ProID = ?;"
    val prepareStatement = conn.prepareStatement(Select_Sql)
    prepareStatement.setInt(1, 3)
    val result = prepareStatement.executeQuery()
    while (result.next()) {
      val vin = result.getString("vin")
      vinSet.add(vin)
    }
    prepareStatement.close()
    conn.close()
    vinSet
  }

}
