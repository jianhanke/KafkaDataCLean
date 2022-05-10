package com.neuexample.Test

import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object KafkaConsumerDemo {
  def main(args: Array[String]): Unit = {
    val prop = new Properties
    prop.put("bootstrap.servers", "hadoop1:6667,hadoop2:6667,hadoop3:6667")
    // 指定消费者组
    prop.put("group.id", "group-MobileChargingVehicle")
    // 指定消费位置: earliest/latest/none
    prop.put("auto.offset.reset", "earliest")
    // 指定消费的key的反序列化⽅式
    prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // 指定消费的value的反序列化⽅式
    prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("enable.auto.commit", "true")
    prop.put("session.timeout.ms", "30000")
    // 得到Consumer实例
    val kafkaConsumer = new KafkaConsumer[String, String](prop)
    // ⾸先需要订阅topic
    kafkaConsumer.subscribe(Collections.singletonList("Gotion-MobileChargingVehicle"))
    // 开始消费数据
    while (true) {
      // 如果Kafak中没有消息，会隔timeout这个值读⼀次。⽐如上⾯代码设置了2秒，也是就2秒后会查⼀次。
      // 如果Kafka中还有消息没有消费的话，会马上去读，⽽不需要等待。
      val msgs: ConsumerRecords[String, String] = kafkaConsumer.poll(2000)
      println(msgs.count())
      val it = msgs.iterator()
      while (it.hasNext) {
        val msg = it.next()
        println(s"partition: ${msg.partition()}, offset: ${msg.offset()}, key: ${msg.key()}, value: ${msg.value()}")
      }
    }
  }
}
