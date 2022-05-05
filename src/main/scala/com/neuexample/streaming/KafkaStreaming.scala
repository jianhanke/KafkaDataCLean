package com.neuexample.streaming

import java.util.Properties

import com.alibaba.fastjson.{JSON, JSONObject}
import com.neuexample.utils.{CheryUtil, GetConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import com.neuexample.streaming.CleanStreaming._


object KafkaStreaming extends Serializable {




  def main(args: Array[String]): Unit = {

    val properties = GetConfig.getProperties("test.properties")

    val spark = SparkSession
      .builder
      .master(properties.getProperty("spark.master"))
      .appName("KafkaFilter")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN");



    val ssc =  new StreamingContext(spark.sparkContext, batchDuration = Seconds(2))
    ssc.checkpoint(properties.getProperty("checkpoint.dir"));



    val props = new Properties
    props.put("bootstrap.servers", properties.getProperty("kafka.bootstrap.servers"))
    props.put("acks", "all")
    props.put("retries", "1")
    props.put("batch.size", "16384") // 单位 byte  约为16k
    props.put("linger.ms", "1") //
    props.put("buffer.memory", "16384") //单位 byte  约为32M
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val bc_producer_props: Broadcast[Properties] = ssc.sparkContext.broadcast(props)
    val bc_topic: Broadcast[String] = ssc.sparkContext.broadcast(properties.getProperty("kafka.output.topic"))

    // Kafka配置参数
    val kafkaParams: Map[String, Object] = Map[String, Object](
      "bootstrap.servers" -> properties.getProperty("kafka.bootstrap.servers"),
      "group.id" ->  properties.getProperty("kafka.input.consumer.groupid"),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      // 自动将偏移重置为最新的偏移，如果是第一次启动程序，应该为smallest，从头开始读
      "auto.offset.reset" -> properties.getProperty("kafka.auto.offset.reset"),
      "enable.auto.commit" -> "true"
    )

    val initStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](properties.getProperty("kafka.input.topic").split(",").toSet, kafkaParams)
    )


    val persistsParts: DStream[String] = initStream.map(_.value()).persist(StorageLevel.MEMORY_ONLY)

    val data: DStream[String] = vehicleClean(persistsParts).persist(StorageLevel.MEMORY_ONLY)

    data.foreachRDD(
     rdd => {
       rdd.foreachPartition(
         partitions => {
           try {
             val producer = new KafkaProducer[String, String](bc_producer_props.value)
             partitions.foreach(line => {
                if(line != null) {
                   producer.send(new ProducerRecord[String, String](bc_topic.value, line))
//                  val meta: RecordMetadata = producer.send(new ProducerRecord[String, String](bc_topic.value, line)).get()
//                  producer.send(new ProducerRecord[String, String](bc_topic.value, line))
                }
             })
             producer.close()
           }catch {
             case ex:Exception=>{
               System.err.println("Process one data error, but program will continue! ", ex)
             }
           }
         }
       )
     }
   )


//      initStream.foreachRDD(rdd => {
//      var offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      initStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
//      })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop(true,true)

  }

}
