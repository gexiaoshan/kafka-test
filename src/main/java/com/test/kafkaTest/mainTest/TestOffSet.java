package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by gexiaoshan on 2018/9/20.
 */
public class TestOffSet {

    public static void main(String[] args) {
        try{
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.204.107:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "300000");
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, Integer.MAX_VALUE);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("session.timeout.ms",60000);
            props.put("max.poll.records", 1);

            KafkaConsumer consumer = new KafkaConsumer<>(props);
//            consumer.subscribe(Arrays.asList("test1"));
//            consumer.poll(100);

            TopicPartition tp = new TopicPartition("test1", 0);

            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
            // 设置 offset 为 0
            offsets.put(tp, new OffsetAndMetadata(1, "reset"));
            consumer.commitSync(offsets);

//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println("consumer>>>" + record.toString());
//            }



        } catch (Exception e){
            e.printStackTrace();
        }

//        LockSupport.park();
    }
}
