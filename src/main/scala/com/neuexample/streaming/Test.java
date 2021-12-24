package com.neuexample.streaming;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Test {
    public static void main(String[] args) {

        String str1="{\"maxTemperature\":18,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10000,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":21,\"soc\":81,\"engineFailuresCount\":0,\"insulationResistance\":3965000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":37,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":2,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":16,\"vin\":\"65758DA2929CBC24E\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":21,\"subsystemVoltageCount\":1,\"gears\":15,\"longitude\":4294967294,\"mileage\":235020,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":9,\"minutes\":27,\"minTemperatureNum\":1,\"batteryCount\":96,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":319648,\"vehicleStatus\":2,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":60,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3331,3329,3329,3329,3329,3329,3330,3329,3330,3329,3330,3331,3331,3329,3329,3329,3329,3329,3329,3329,3329,3329,3329,3330,3331,3329,3329,3329,3329,3329,3330,3329,3330,3330,3330,3331,3331,3329,3329,3329,3329,3329,3329,3329,3329,3329,3329,3331,3331,3329,3329,3329,3329,3329,3330,3330,3330,3329,3329,3332,3331,3330,3329,3329,3329,3329,3330,3330,3330,3330,3330,3331,3331,3330,3330,3330,3330,3330,3330,3330,3330,3330,3330,3331,3331,3330,3330,3330,3329,3329,3330,3329,3330,3330,3329,3331],\"chargeStatus\":3,\"speed\":65534,\"deviceTypeOverFilling\":false,\"controllerInputVoltage\":30,\"operationMode\":254,\"current\":10000,\"cellCount\":96,\"totalCurrent\":10000,\"minTemperature\":17,\"monomerBatteryUnderVoltage\":false,\"temperature\":17,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryMaxVoltage\":3332,\"batteryHighTemperature\":false,\"dcStatus\":false,\"hours\":7,\"riveMotorDataNum\":254,\"brakingSystem\":false,\"batteryMinVoltage\":3329,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":319648,\"subsystemVoltageDataNum\":1,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[57,57,57,57,57,57,57,57,58,57,57,57,58,57,57,58],\"time\":1640051303431}";

        String str2="{\"driveMotorControllerTemperature\":false,\"maxTemperature\":-3,\"controllerDcBusbarCurrent\":0,\"dcTemperature\":false,\"batteryNumber\":0,\"temperatureDifferential\":false,\"year\":21,\"soc\":49,\"engineFailuresCount\":0,\"insulationResistance\":3018000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":22,\"driveMotorTemperature\":false,\"deviceTypeOverVoltage\":false,\"minVoltagebatteryNum\":1,\"engineFailuresCodes\":[],\"temperatureProbeCount\":34,\"highPressureInterlock\":false,\"vin\":\"LB3752CW2MAOSSCHN\",\"driveMotorCount\":0,\"vehicleFactory\":\"5\",\"day\":21,\"subsystemVoltageCount\":0,\"gears\":0,\"longitude\":0,\"mileage\":499,\"socHigh\":false,\"subsystemTemperatureCount\":0,\"level\":0,\"maxTemperatureNum\":1,\"minutes\":48,\"minTemperatureNum\":4,\"batteryCount\":0,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":0,\"totalVoltage\":367728,\"status\":0,\"vehicleStatus\":0,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":7,\"latitude\":0,\"torque\":0,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":\"[3331,3329,3329,3329,3329,3329,3330,3329]\",\"chargeStatus\":3,\"speed\":0,\"controllerInputVoltage\":0,\"deviceTypeOverFilling\":false,\"current\":0,\"operationMode\":0,\"cellCount\":102,\"totalCurrent\":0,\"minTemperature\":-4,\"monomerBatteryUnderVoltage\":false,\"temperature\":-4,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryHighTemperature\":false,\"batteryMaxVoltage\":3606,\"dcStatus\":false,\"hours\":9,\"riveMotorDataNum\":0,\"brakingSystem\":false,\"batteryMinVoltage\":3604,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":367728,\"subsystemVoltageDataNum\":0,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[37,37,37,36,37,36,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37],\"time\":1640051302000}";

        JSONObject json1 = JSON.parseObject(str1);
        JSONObject json2 = JSON.parseObject(str2);

        String cellString = json1.getString("cellVoltages");
        System.out.println(cellString);

    }






}
