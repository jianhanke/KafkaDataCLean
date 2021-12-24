package com.neuexample.streaming;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;

public class Test2 {

    public static void main(String[] args) {



        String str14="{\"driveMotorControllerTemperature\":false,\"maxTemperature\":25,\"controllerDcBusbarCurrent\":0,\"dcTemperature\":false,\"batteryNumber\":0,\"temperatureDifferential\":false,\"year\":21,\"soc\":88,\"engineFailuresCount\":0,\"insulationResistance\":3045000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":23,\"driveMotorTemperature\":false,\"deviceTypeOverVoltage\":false,\"minVoltagebatteryNum\":67,\"engineFailuresCodes\":[],\"temperatureProbeCount\":34,\"highPressureInterlock\":false,\"vin\":\"my_test\",\"driveMotorCount\":0,\"vehicleFactory\":\"5\",\"day\":20,\"subsystemVoltageCount\":0,\"gears\":0,\"longitude\":0,\"mileage\":134,\"socHigh\":false,\"subsystemTemperatureCount\":0,\"level\":0,\"maxTemperatureNum\":13,\"minutes\":42,\"minTemperatureNum\":1,\"batteryCount\":0,\"insulation\":false,\"month\":12,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":0,\"totalVoltage\":377400,\"status\":0,\"vehicleStatus\":0,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":74,\"latitude\":0,\"torque\":0,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700,3700],\"chargeStatus\":3,\"speed\":0,\"controllerInputVoltage\":0,\"deviceTypeOverFilling\":false,\"current\":0,\"operationMode\":0,\"cellCount\":102,\"totalCurrent\":0,\"minTemperature\":25,\"monomerBatteryUnderVoltage\":false,\"temperature\":25,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryHighTemperature\":false,\"batteryMaxVoltage\":3700,\"dcStatus\":false,\"hours\":17,\"riveMotorDataNum\":0,\"brakingSystem\":false,\"batteryMinVoltage\":3700,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":377400,\"timeStamp\":1639993343,\"subsystemVoltageDataNum\":0,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":\"[65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65,65]\",\"time\":1639993343000}";


        JSONObject json = JSON.parseObject(str14);
        System.out.println(json.toString());


        JSONObject json2 = JSON.parseObject(str14, Feature.OrderedField);
        System.out.println(json2.toString());

    }

}
