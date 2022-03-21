package com.neuexample.Test

import com.alibaba.fastjson.{JSON, JSONObject}
import com.neuexample.streaming.CleanStreaming.stringToIntArray

object TestApp {

  def main(args: Array[String]): Unit = {

    var str="{\"alarmInfo\":0,\"batteryConsistencyPoor\":false,\"batteryCount\":0,\"batteryHighTemperature\":false,\"batteryMaxVoltage\":3653,\"batteryMinVoltage\":3646,\"batteryNumber\":0,\"brakingSystem\":false,\"cellCount\":102,\"cellVoltages\":[3651,3649,3649,3649,3647,3649,3650,3649,3649,3648,3649,3650,3650,3650,3650,3649,3648,3651,3649,3646,3649,3649,3648,3651,3648,3648,3649,3648,3648,3649,3650,3648,3648,3649,3648,3649,3649,3649,3650,3650,3650,3651,3653,3649,3648,3648,3648,3650,3649,3649,3648,3649,3648,3651,3649,3649,3649,3648,3648,3649,3647,3647,3649,3648,3648,3649,3650,3649,3648,3649,3647,3647,3648,3649,3648,3649,3648,3650,3650,3649,3649,3649,3648,3649,3649,3648,3648,3649,3649,3650,3652,3648,3649,3649,3649,3649,3648,3649,3649,3649,3648,3648],\"chargeStatus\":3,\"controllerDcBusbarCurrent\":0,\"controllerInputVoltage\":0,\"current\":33200,\"day\":19,\"dcStatus\":true,\"dcTemperature\":false,\"deviceFailuresCodes\":[],\"deviceFailuresCount\":0,\"deviceTypeDontMatch\":false,\"deviceTypeOverFilling\":false,\"deviceTypeOverVoltage\":false,\"deviceTypeUnderVoltage\":false,\"driveMotorControllerTemperature\":false,\"driveMotorCount\":0,\"driveMotorFailuresCodes\":[],\"driveMotorFailuresCount\":0,\"driveMotorTemperature\":false,\"engineFailuresCodes\":[],\"engineFailuresCount\":0,\"gears\":0,\"highPressureInterlock\":false,\"hours\":22,\"insulation\":false,\"insulationResistance\":3024000,\"latitude\":0,\"level\":0,\"longitude\":0,\"maxTemperature\":54,\"maxTemperatureNum\":1,\"maxTemperatureSystemNum\":1,\"maxVoltageSystemNum\":1,\"maxVoltagebatteryNum\":43,\"mileage\":523,\"minTemperature\":53,\"minTemperatureNum\":2,\"minTemperatureSystemNum\":1,\"minVoltageSystemNum\":1,\"minVoltagebatteryNum\":20,\"minutes\":47,\"monomerBatteryOverVoltage\":false,\"monomerBatteryUnderVoltage\":false,\"month\":1,\"operationMode\":0,\"otherFailuresCodes\":[],\"otherFailuresCount\":0,\"probeTemperatures\":[54,53,54,54,54,54,54,54,54,53,53,53,54,54,54,53,53,53,53,53,54,54,54,54,54,53,54,53,54,54,54,54,54,54],\"riveMotorDataNum\":0,\"seconds\":58,\"soc\":42,\"socHigh\":false,\"socJump\":false,\"socLow\":false,\"speed\":0,\"status\":0,\"subsystemTemperatureCount\":0,\"subsystemTemperatureDataNum\":0,\"subsystemVoltageCount\":0,\"subsystemVoltageDataNum\":0,\"temperature\":0,\"temperatureDifferential\":false,\"temperatureProbeCount\":34,\"time\":1642603678000,\"torque\":0,\"totalCurrent\":0,\"totalVoltage\":0,\"vehicleFactory\":\"5\",\"vehicleStatus\":0,\"vin\":\"LB3752CW8MAOSSTSG\",\"voltage\":3715,\"year\":22}";

    val json: JSONObject = JSON.parseObject(str)

    val cellVoltages: Array[Int] = stringToIntArray(json.getString("cellVoltages"))
    println(cellVoltages.mkString(","))
    println(cellVoltages.max + "," + cellVoltages.indexOf(cellVoltages.max) )
    println(cellVoltages.min + "," + cellVoltages.indexOf(cellVoltages.min) )
    json.put("maxVoltagebatteryNum", cellVoltages.indexOf(cellVoltages.max))
    json.put("minVoltagebatteryNum", cellVoltages.indexOf(cellVoltages.min))


    println()
    val probeTeptureArray: Array[Int] = stringToIntArray(json.getString("probeTemperatures"))
    println(probeTeptureArray.mkString(","))
    println(probeTeptureArray.max + "," + probeTeptureArray.indexOf(probeTeptureArray.max) )
    println(probeTeptureArray.min + "," + probeTeptureArray.indexOf(probeTeptureArray.min) )
    json.put("maxTemperatureNum", probeTeptureArray.indexOf(probeTeptureArray.max))
    json.put("minTemperatureNum", probeTeptureArray.indexOf(probeTeptureArray.min))

    println(json)
  }

}
