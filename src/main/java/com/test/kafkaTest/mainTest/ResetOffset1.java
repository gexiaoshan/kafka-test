package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import sun.plugin2.message.Message;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by gexiaoshan on 2018/10/10.
 */
public class ResetOffset1 {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.204.107:9092");
        props.put("group.id", "myGroup");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, Message> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        //consumer.subscribe(Arrays.asList(topic));	//"deviceInfoTopic"
        TopicPartition topicPartition = new TopicPartition("test1", 0);
        consumer.assign(Arrays.asList(topicPartition));
        consumer.seek(topicPartition, 2);
        consumer.commitSync();
//        consumer.close();


    }
}
