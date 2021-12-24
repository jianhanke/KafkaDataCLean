package com.neuexample.streaming;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

import com.neuexample.utils.GetConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyComsumer {

   static Properties properties = GetConfig.getProperties("test.properties");
    private static KafkaConsumer<String, String> consumer;


    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", properties.getProperty("kafka.bootstrap.servers"));
        props.put("group.id", "test-dwd2");
        //设置手动提交
        props.put("enable.auto.commit", "false");
        //这个可以设置大一点
        props.put("session.timeout.ms", "30000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);

        String topicName = "vehicledwd";
        Integer partition = 2;
        Integer offset = 52742872;
        consume(topicName, partition, offset);



    }

    public static void consume(String topicName, Integer partition, Integer offset) {

        //用于分配topic和partition
        consumer.assign(Arrays.asList(new TopicPartition(topicName, partition)));
        //不改变当前offset，指定从这个topic和partition的开始位置获取。
        consumer.seek(new TopicPartition(topicName, partition),offset);

        //consumer.seekToBeginning(Arrays.asList(new TopicPartition(topicName, partition)));

        PropertyConfigurator.configure("log4j.properties");

        Logger logger = Logger.getLogger(Test.class);

        for (int i = 0; i < 1000; i++) {
            //poll消息timeout可以设置大一点，由此设置1000，拿不到数据，debug查看cluster发现没有信息
            ConsumerRecords<String, String> records = consumer.poll(30000);
            for (ConsumerRecord record : records) {
                String s = "partition:" + record.partition() + ",offset:" + record.offset() + ",key:" + record.key() + ",offset:" + record.value();
                logger.error(s);
            }
            System.out.println(i);
        }

    }

}
