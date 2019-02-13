package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by gexiaoshan on 2018/9/28.
 */
public class ConsumerOffset {

    public static void main(String[] args) {
        Properties properties = new Properties();
        //服务器
        properties.put("bootstrap.servers", "192.168.204.107:9092");

        //消费组id
        properties.put("group.id", "mygroup");
        //
        properties.put("enable.auto.commit", "true");//自动提交
        properties.put("auto.commit.interval.ms", "100");//自动提交间隔
        properties.put("max.poll.records", 2);//这个值的意思是每次poll拉取数据的最大任务数，

        properties.put("heartbeat.interval.ms",900);//心跳时间
        properties.put("session.timeout.ms", 9000);//会话超时时间

        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("test4"));

//        kafkaConsumer.

        while (true) {
            //timeout：kafka中的数据未就绪情况下，等待的最长时间，如果设置为0，立即返回已经就绪的数据,若有已就绪数据立即返回
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
//            System.out.println("拉取一次");
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.toString());
            }

        }
    }
}
