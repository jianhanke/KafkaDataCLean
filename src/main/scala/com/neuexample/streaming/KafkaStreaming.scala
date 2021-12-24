package com.neuexample.streaming

import java.util.Properties

import com.neuexample.utils.GetConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import com.neuexample.streaming.CleanStreaming._
import org.apache.log4j.PropertyConfigurator


object KafkaStreaming extends Serializable {


  val properties = GetConfig.getProperties("test.properties")
  // PropertyConfigurator.configure("log4j.properties");

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master(properties.getProperty("spark.master"))
      .appName("SparkStreamingKafkaFilter")
      .getOrCreate()

    val sc = spark.sparkContext
    sc.setLogLevel("ERROR");


    val ssc =  new StreamingContext(sc, batchDuration = Seconds(2))
    ssc.checkpoint(properties.getProperty("checkpoint.dir"));

    val topic: String = properties.getProperty("kafka.output.topic")
    val topicsSet: Set[String] = properties.getProperty("kafka.input.topic").split(",").toSet
    val bc_topic: Broadcast[String] = ssc.sparkContext.broadcast(topic)
    //val bc_cur_year: Broadcast[String] = ssc.sparkContext.broadcast(properties.getProperty("cur.year"))

    val props = new Properties
    props.put("bootstrap.servers", properties.getProperty("kafka.bootstrap.servers"))
    props.put("acks", "all")
    props.put("retries", "1")
    props.put("batch.size", "16384") // 单位 byte  约为16k
    props.put("linger.ms", "1") //
    props.put("buffer.memory", "16384") //单位 byte  约为32M
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val bc_props: Broadcast[Properties] = ssc.sparkContext.broadcast(props)


    // Kafka配置参数
    val kafkaParams: Map[String, Object] = Map[String, Object](
      "bootstrap.servers" -> properties.getProperty("kafka.bootstrap.servers"),
      "group.id" ->  properties.getProperty("kafka.input.consumer.groupid"),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      // 自动将偏移重置为最新的偏移，如果是第一次启动程序，应该为smallest，从头开始读
      "auto.offset.reset" -> properties.getProperty("kafka.auto.offset.reset")
    )

    val initStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams)
    )


    val persistsParts: DStream[String] = initStream.map(_.value()).persist(StorageLevel.MEMORY_ONLY)

    val cleanVehicleDStream: DStream[String] = vehicleClean(persistsParts)

    val data: DStream[String] = cleanVehicleDStream.persist(StorageLevel.MEMORY_ONLY)


    data.foreachRDD(
     rdd=>{
       rdd.foreachPartition(
         partitions=>{
           try {
             val producer = new KafkaProducer[String, String](bc_props.value)
             partitions.foreach(line => {
     //        println(line)
               val meta: RecordMetadata = producer.send(new ProducerRecord[String, String](bc_topic.value, line)).get()
              println("offset:" + meta.offset + "," + meta.toString)
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


      initStream.foreachRDD(rdd => {
      var offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      initStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
      })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop(true,true)

  }

}
