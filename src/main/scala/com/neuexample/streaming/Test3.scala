package com.neuexample.streaming

import java.util

import com.alibaba.fastjson.{JSON, JSONObject}

import scala.collection.mutable.ListBuffer


object Test3 {

  def main(args: Array[String]): Unit = {

    val str1 = "{\"maxTemperature\":18,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10000,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":21,\"soc\":81,\"engineFailuresCount\":0,\"insulationResistance\":3965000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":37,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":2,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":16,\"vin\":\"65758DA2929CBC24E\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":21,\"subsystemVoltageCount\":1,\"gears\":15,\"longitude\":4294967294,\"mileage\":235020,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":9,\"minutes\":27,\"minTemperatureNum\":1,\"batteryCount\":96,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":319648,\"vehicleStatus\":2,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":60,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3331,3329,3329,3329,3329,3329,3330,3329,3330,3329,3330,3331,3331,3329,3329,3329,3329,3329,3329,3329,3329,3329,3329,3330,3331,3329,3329,3329,3329,3329,3330,3329,3330,3330,3330,3331,3331,3329,3329,3329,3329,3329,3329,3329,3329,3329,3329,3331,3331,3329,3329,3329,3329,3329,3330,3330,3330,3329,3329,3332,3331,3330,3329,3329,3329,3329,3330,3330,3330,3330,3330,3331,3331,3330,3330,3330,3330,3330,3330,3330,3330,3330,3330,3331,3331,3330,3330,3330,3329,3329,3330,3329,3330,3330,3329,3331],\"chargeStatus\":3,\"speed\":65534,\"deviceTypeOverFilling\":false,\"controllerInputVoltage\":30,\"operationMode\":254,\"current\":10000,\"cellCount\":96,\"totalCurrent\":10000,\"minTemperature\":17,\"monomerBatteryUnderVoltage\":false,\"temperature\":17,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryMaxVoltage\":3332,\"batteryHighTemperature\":false,\"dcStatus\":false,\"hours\":7,\"riveMotorDataNum\":254,\"brakingSystem\":false,\"batteryMinVoltage\":3329,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":319648,\"subsystemVoltageDataNum\":1,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[57,57,57,57,57,57,57,57,58,57,57,57,58,57,57,58],\"time\":1640051303431}"

    val str3 = "{\"maxTemperature\":18,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10000,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":21,\"soc\":81,\"engineFailuresCount\":0,\"insulationResistance\":3965000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":37,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":2,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":16,\"vin\":\"65758DA2929CBC24E\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":21,\"subsystemVoltageCount\":1,\"gears\":15,\"longitude\":4294967294,\"mileage\":235020,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":9,\"minutes\":27,\"minTemperatureNum\":1,\"batteryCount\":96,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":319648,\"vehicleStatus\":2,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":60,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"time\":1640051303431}"

    val str2 = "{\"driveMotorControllerTemperature\":false,\"maxTemperature\":-3,\"controllerDcBusbarCurrent\":0,\"dcTemperature\":false,\"batteryNumber\":0,\"temperatureDifferential\":false,\"year\":21,\"soc\":49,\"engineFailuresCount\":0,\"insulationResistance\":3018000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":22,\"driveMotorTemperature\":false,\"deviceTypeOverVoltage\":false,\"minVoltagebatteryNum\":1,\"engineFailuresCodes\":[],\"temperatureProbeCount\":34,\"highPressureInterlock\":false,\"vin\":\"LB3752CW2MAOSSCHN\",\"driveMotorCount\":0,\"vehicleFactory\":\"5\",\"day\":21,\"subsystemVoltageCount\":0,\"gears\":0,\"longitude\":0,\"mileage\":499,\"socHigh\":false,\"subsystemTemperatureCount\":0,\"level\":0,\"maxTemperatureNum\":1,\"minutes\":48,\"minTemperatureNum\":4,\"batteryCount\":0,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":0,\"totalVoltage\":367728,\"status\":0,\"vehicleStatus\":0,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":7,\"latitude\":0,\"torque\":0,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":\"[3331,3329,3329,3329,3329,3329,3330,3329]\",\"chargeStatus\":3,\"speed\":0,\"controllerInputVoltage\":0,\"deviceTypeOverFilling\":false,\"current\":0,\"operationMode\":0,\"cellCount\":102,\"totalCurrent\":0,\"minTemperature\":-4,\"monomerBatteryUnderVoltage\":false,\"temperature\":-4,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryHighTemperature\":false,\"batteryMaxVoltage\":3606,\"dcStatus\":false,\"hours\":9,\"riveMotorDataNum\":0,\"brakingSystem\":false,\"batteryMinVoltage\":3604,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":367728,\"subsystemVoltageDataNum\":0,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[37,37,37,36,37,36,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37],\"time\":1640051302000}"

    val json1: JSONObject = JSON.parseObject(str3)
    val json2: JSONObject = JSON.parseObject(str2)



    //json2.put("cellVoltages",stringToArray(json1.getString("cellVoltages")) )
    println(json2)

    val str4="{\n\t\"maxTemperature\": 54,\n\t\"driveMotorControllerTemperature\": false,\n\t\"dcTemperature\": false,\n\t\"controllerDcBusbarCurrent\": 10000,\n\t\"batteryNumber\": 1,\n\t\"temperatureDifferential\": false,\n\t\"year\": 21,\n\t\"soc\": 59,\n\t\"engineFailuresCount\": 0,\n\t\"insulationResistance\": 3013000,\n\t\"otherFailuresCodes\": [],\n\t\"maxVoltageSystemNum\": 1,\n\t\"seconds\": 49,\n\t\"driveMotorTemperature\": false,\n\t\"minVoltagebatteryNum\": 3,\n\t\"deviceTypeOverVoltage\": false,\n\t\"engineFailuresCodes\": [],\n\t\"temperatureProbeCount\": 5,\n\t\"vin\": \"1B9874D427143EFCB\",\n\t\"highPressureInterlock\": false,\n\t\"vehicleFactory\": \"1\",\n\t\"driveMotorCount\": 1,\n\t\"day\": 22,\n\t\"subsystemVoltageCount\": 1,\n\t\"gears\": 0,\n\t\"longitude\": 4294967294,\n\t\"mileage\": 3715,\n\t\"socHigh\": false,\n\t\"subsystemTemperatureCount\": 1,\n\t\"level\": 0,\n\t\"maxTemperatureNum\": 1,\n\t\"minutes\": 8,\n\t\"minTemperatureNum\": 3,\n\t\"batteryCount\": 30,\n\t\"insulation\": false,\n\t\"month\": 12,\n\t\"deviceFailuresCount\": 0,\n\t\"socJump\": false,\n\t\"subsystemTemperatureDataNum\": 1,\n\t\"totalVoltage\": 991,\n\t\"vehicleStatus\": 3,\n\t\"status\": 254,\n\t\"deviceFailuresCodes\": [],\n\t\"driveMotorFailuresCodes\": [],\n\t\"maxVoltagebatteryNum\": 18,\n\t\"latitude\": 4294967294,\n\t\"torque\": 65534,\n\t\"deviceTypeDontMatch\": false,\n\t\"socLow\": false,\n\t\"alarmInfo\": 0,\n\t\"cellVoltages\": [3303, 3302, 3301, 3301, 3300, 3301, 3305, 3304, 3305, 3305, 3304, 3303, 3305, 3304, 3306, 3305, 3303, 3308, 3308, 3306, 3306, 3305, 3307, 3304, 3303, 3304, 3304, 3305, 3304, 3305],\n\t\"chargeStatus\": 3,\n\t\"speed\": 65534,\n\t\"deviceTypeOverFilling\": false,\n\t\"controllerInputVoltage\": 0,\n\t\"operationMode\": 254,\n\t\"current\": 10000,\n\t\"cellCount\": 30,\n\t\"totalCurrent\": 10000,\n\t\"minTemperature\": 53,\n\t\"monomerBatteryUnderVoltage\": false,\n\t\"temperature\": 254,\n\t\"deviceTypeUnderVoltage\": false,\n\t\"monomerBatteryOverVoltage\": false,\n\t\"batteryConsistencyPoor\": false,\n\t\"batteryMaxVoltage\": 3309,\n\t\"batteryHighTemperature\": false,\n\t\"dcStatus\": false,\n\t\"hours\": 18,\n\t\"riveMotorDataNum\": 254,\n\t\"brakingSystem\": false,\n\t\"batteryMinVoltage\": 3301,\n\t\"minTemperatureSystemNum\": 1,\n\t\"driveMotorFailuresCount\": 0,\n\t\"maxTemperatureSystemNum\": 1,\n\t\"voltage\": 991,\n\t\"subsystemVoltageDataNum\": 1,\n\t\"minVoltageSystemNum\": 1,\n\t\"otherFailuresCount\": 0,\n\t\"probeTemperatures\": [60, 60, 60, 60, 60],\n\t\"time\": 1640167719168\n}";
    val json: JSONObject = JSON.parseObject(str4)
    val str: String = json.getString("cellVoltages")

    val arr: Array[Int] = stringToList(str)

    json.put("ce",arr.toString );
    println(json)
    println("====");

    println( JSON.parseObject(json.toString()).getString("ce"))
    println( json.getString("ce"))

    // val ints: Array[Int] = stringToIntArray(json.getString("ce"))



//    val list = new ListBuffer[Int]
//    list


  }

  /**
    *
    * @param str  "[1,2,3]"
    * @return      [1,2,3]
    */
  def stringToList(str:String): Array[Int]={

    println(str)
    if(str==null || str.equals("[]") ) {
      println("jianlail")
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


  def stringToList2(str:String): Array[Int]={

    println(str)
    if(str==null || str.equals("[]") ) {
      println("jianlail")
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


  /**
    *
    * @param str  ["1","2","3"]
    * @return      Array[1,2,3]
    */
  def stringToIntArray(str:String):Array[Int]={


    // println(str)
    if(str!=null && str.length>2) {
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

}
