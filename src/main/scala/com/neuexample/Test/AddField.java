package com.neuexample.Test;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;


public class AddField {

    public static void main(String[] args)throws Exception {

        Properties props = new Properties();
        props.put("bootstrap.servers","39.103.238.103:6667");
        props.put("acks","all");
        props.put("retries",1);
        props.put("batch.size",16384);  //  byte
        props.put("linger.ms",1000);   //
        props.put("buffer.memory", 33554432);// byte
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        String str_add_custom="{\"customField6\":\"{6}\",\"customField1\":\"{1}\",\"customField2\":\"{2}\",\"customField3\":\"{3}\",\"customField4\":\"{4}\",\"customField5\":\"{5}\",\"maxTemperature\":92,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10730,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":22,\"soc\":57,\"engineFailuresCount\":0,\"insulationResistance\":40411000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":30,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":13,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":5,\"vin\":\"my_test\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":18,\"subsystemVoltageCount\":1,\"gears\":46,\"longitude\":4294967294,\"mileage\":74220,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":1,\"minutes\":46,\"minTemperatureNum\":1,\"batteryCount\":30,\"insulation\":false,\"month\":1,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":940,\"vehicleStatus\":1,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":19,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3137,3138,3135,3139,3141,3140,3137,3138,3142,3143,3137,3139,3135,3142,3141,3142,3139,3143,3148,3141,3144,3140,3143,3141,3137,3142,3142,3144,3141,3148],\"chargeStatus\":3,\"speed\":65534,\"deviceTypeOverFilling\":false,\"controllerInputVoltage\":910,\"operationMode\":254,\"current\":10779,\"cellCount\":30,\"totalCurrent\":10779,\"minTemperature\":49,\"monomerBatteryUnderVoltage\":false,\"temperature\":254,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryMaxVoltage\":3143,\"batteryHighTemperature\":false,\"dcStatus\":false,\"hours\":14,\"riveMotorDataNum\":254,\"brakingSystem\":false,\"batteryMinVoltage\":3129,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":940,\"subsystemVoltageDataNum\":1,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[49,49,49,49,49],\"time\":1642488420523}";

        String str="{\"maxTemperature\":88,\"driveMotorControllerTemperature\":false,\"dcTemperature\":false,\"controllerDcBusbarCurrent\":10730,\"batteryNumber\":1,\"temperatureDifferential\":false,\"year\":22,\"soc\":57,\"engineFailuresCount\":0,\"insulationResistance\":40411000,\"otherFailuresCodes\":[],\"maxVoltageSystemNum\":1,\"seconds\":30,\"driveMotorTemperature\":false,\"minVoltagebatteryNum\":13,\"deviceTypeOverVoltage\":false,\"engineFailuresCodes\":[],\"temperatureProbeCount\":5,\"vin\":\"my_test\",\"highPressureInterlock\":false,\"vehicleFactory\":\"1\",\"driveMotorCount\":1,\"day\":18,\"subsystemVoltageCount\":1,\"gears\":46,\"longitude\":4294967294,\"mileage\":74220,\"socHigh\":false,\"subsystemTemperatureCount\":1,\"level\":0,\"maxTemperatureNum\":1,\"minutes\":46,\"minTemperatureNum\":1,\"batteryCount\":30,\"insulation\":false,\"month\":1,\"deviceFailuresCount\":0,\"socJump\":false,\"subsystemTemperatureDataNum\":1,\"totalVoltage\":940,\"vehicleStatus\":1,\"status\":254,\"deviceFailuresCodes\":[],\"driveMotorFailuresCodes\":[],\"maxVoltagebatteryNum\":19,\"latitude\":4294967294,\"torque\":65534,\"deviceTypeDontMatch\":false,\"socLow\":false,\"alarmInfo\":0,\"cellVoltages\":[3137,3138,3135,3139,3141,3140,3137,3138,3142,3143,3137,3139,3135,3142,3141,3142,3139,3143,3148,3141,3144,3140,3143,3141,3137,3142,3142,3144,3141,3148],\"chargeStatus\":3,\"speed\":65534,\"deviceTypeOverFilling\":false,\"controllerInputVoltage\":910,\"operationMode\":254,\"current\":10779,\"cellCount\":30,\"totalCurrent\":10779,\"minTemperature\":49,\"monomerBatteryUnderVoltage\":false,\"temperature\":254,\"deviceTypeUnderVoltage\":false,\"monomerBatteryOverVoltage\":false,\"batteryConsistencyPoor\":false,\"batteryMaxVoltage\":3143,\"batteryHighTemperature\":false,\"dcStatus\":false,\"hours\":14,\"riveMotorDataNum\":254,\"brakingSystem\":false,\"batteryMinVoltage\":3129,\"minTemperatureSystemNum\":1,\"driveMotorFailuresCount\":0,\"maxTemperatureSystemNum\":1,\"voltage\":940,\"subsystemVoltageDataNum\":1,\"minVoltageSystemNum\":1,\"otherFailuresCount\":0,\"probeTemperatures\":[49,49,49,49,49],\"time\":1642488420523}";

        RecordMetadata meta = producer.send(new ProducerRecord<String, String>("addfield", str_add_custom)).get();
        System.out.println("offset:"+meta.offset()+","+meta.toString());
        producer.close();
    }

}
