package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.consumer.*;

import java.util.*;

/**
 * Created by gexiaoshan on 2018/9/21.
 * 手动提交
 */
public class ConsumerShouDong {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.204.107:9092");
        properties.put("group.id", "myGroup");
        properties.put("enable.auto.commit", "false");//设置为手动提交
        properties.put("session.timeout.ms", "10000");
        properties.put("max.poll.interval.ms", "3000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("test1"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("consumer:" + record.toString());
                }

//                Thread.sleep(6000);
                //手动提交 - 更改offset
                kafkaConsumer.commitAsync();

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }
}
