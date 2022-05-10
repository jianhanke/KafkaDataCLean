package com.neuexample.utils

import com.neuexample.utils.GetConfig.getMysqlConn
import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast

import scala.collection.mutable.HashSet



object CheryUtil {
  @volatile private var vinSetDJ1811A: HashSet[String] = _
  @volatile private var vinSetDJ1903: HashSet[String] = _
  @volatile private var vinSetDJ1902: HashSet[String] = _
  @volatile private var vinSetDJ2015: HashSet[String] = _

  val properties = GetConfig.getProperties("test.properties")

  /**
   *
   * @return
   */
  def creatInstanceDJ1811A():HashSet[String] = {
    if (vinSetDJ1811A == null) {
      synchronized {
        if (vinSetDJ1811A == null) {
          println("-----#GetDJ1811A-----")
          vinSetDJ1811A = getDJ1811AVinData
        }
      }
    }
    vinSetDJ1811A
  }

  /**
   *
   */
  def creatInstanceDJ1903():HashSet[String] = {
    if (vinSetDJ1903 == null) {
      synchronized {
        if (vinSetDJ1903 == null) {
          println("-----#GetDJ1903-----")
          vinSetDJ1903 = getDJ1903VinData
        }
      }
    }
    vinSetDJ1903
  }

  /**
   *
   */
  def creatInstanceDJ2015():HashSet[String] = {
    if (vinSetDJ2015 == null) {
      synchronized {
        if (vinSetDJ2015 == null) {
          println("-----##GetDJ2015-----")
          vinSetDJ2015 = getDJ2015VinData
        }
      }
    }
    vinSetDJ2015
  }

  /**
   *
   * @return
   */
  def creatInstanceDJ1902():HashSet[String] = {
    if (vinSetDJ1902 == null) {
      synchronized {
        if (vinSetDJ1902 == null) {
          println("-----#GetDJ1902-----")
          vinSetDJ1902 = getDJ1902VinData
        }
      }
    }
    vinSetDJ1902
  }

  /**
   *
   * @return
   */
  private def getDJ1811AVinData(): HashSet[String] = {
    val conn = getMysqlConn(properties)
    val vinSet:HashSet[String] =HashSet[String]()
    val Select_Sql = "select vin from battery.GX_Vin where ProID = ?;"
    val prepareStatement = conn.prepareStatement(Select_Sql)
    prepareStatement.setInt(1, 1)
    val result = prepareStatement.executeQuery()
    while (result.next()) {
      val vin = result.getString("vin")
      vinSet.add(vin)
    }
    prepareStatement.close()
    conn.close()
    vinSet
  }

  /**
   *
   * @return
   */
  private def getDJ1903VinData(): HashSet[String] = {
    val conn = getMysqlConn(properties)
    val vinSet:HashSet[String] =HashSet[String]()
    val Select_Sql = "select vin from battery.GX_Vin where ProID = ?;"
    val prepareStatement = conn.prepareStatement(Select_Sql)
    prepareStatement.setInt(1, 2)
    val result = prepareStatement.executeQuery()
    while (result.next()) {
      val vin = result.getString("vin")
      vinSet.add(vin)
    }
    prepareStatement.close()
    conn.close()
    vinSet
  }

  /**
   *
   * @return
   */
  private def getDJ1902VinData(): HashSet[String] = {
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

  private def getDJ2015VinData(): HashSet[String] = {
    val conn = getMysqlConn(properties)
    val vinSet:HashSet[String] =HashSet[String]()
    val Select_Sql = "select vin from battery.GX_Vin where ProID = ?;"
    val prepareStatement = conn.prepareStatement(Select_Sql)
    prepareStatement.setInt(1, 4)
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
