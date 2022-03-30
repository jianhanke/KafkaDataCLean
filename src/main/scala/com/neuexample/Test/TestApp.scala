package com.neuexample.Test

import com.alibaba.fastjson.{JSON, JSONObject}

object TestApp {

  def main(args: Array[String]): Unit = {

    var str="{\"maxTemperature\":63,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10000,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":22,\"soc\":34,\"engineFailuresCount\":0,\"insulationResistance\":40729000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":18,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":8,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":5,\"vin\":\"703C6670C121BFC85\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":19,\"subsystemVoltageCount\":1,\"gears\":15,\"longitude\":4294967294,\"mileage\":26200,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":2,\"minutes\":46,\"minTemperatureNum\":1,\"batteryCount\":30,\"insulation\":false,\"month\":1,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":996,\"vehicleStatus\":2,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":1,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3326,3322,3321,3321,3320,3321,3320,3319,3321,3322,3320,3321,3324,3321,3321,3320,3320,3324,3321,3321,3320,3320,3321,3322,3322,3321,3320,3320,3320,3323],\"chargeStatus\":1,\"speed\":65534,\"deviceTypeOverFilling\":false,\"controllerInputVoltage\":970,\"operationMode\":254,\"current\":9858,\"cellCount\":30,\"totalCurrent\":9858,\"minTemperature\":62,\"monomerBatteryUnderVoltage\":false,\"temperature\":254,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryMaxVoltage\":3326,\"batteryHighTemperature\":false,\"dcStatus\":false,\"hours\":22,\"riveMotorDataNum\":254,\"brakingSystem\":false,\"batteryMinVoltage\":3319,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":996,\"subsystemVoltageDataNum\":1,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[62,63,62,63,63],\"time\":1642603679440}";

     var json: JSONObject = JSON.parseObject(str)
     val bytes: Array[Byte] = json.getBytes("cellVoltages")

    println(bytes.mkString(","))

  }




}
