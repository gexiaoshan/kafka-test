package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by gexiaoshan on 2018/9/19.
 * 自动 commit 提交的情况
 */
public class ConsumerZiDong {

    public static void main(String[] args) {
        Properties properties = new Properties();
        //服务器
        properties.put("bootstrap.servers", "192.168.204.107:9092");
        //消费组id
        properties.put("group.id", "myGroup");
        //
        properties.put("enable.auto.commit", "true");//自动提交

        properties.put("max.poll.records", 2);

        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        //ConsumerRebalanceListener
        TestConsumerRebalanceListener cRl = new TestConsumerRebalanceListener();
        kafkaConsumer.subscribe(Arrays.asList("test1"), cRl);

        TopicPartition tp = new TopicPartition("test1", 1);
        //指定分区
//        kafkaConsumer.assign(Arrays.asList(tp));
//        kafkaConsumer.seek(tp, 1);

        while (true) {
            //timeout：kafka中的数据未就绪情况下，等待的最长时间，如果设置为0，立即返回已经就绪的数据,若有已就绪数据立即返回
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
//            System.out.println("拉取一次");
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("consumer:" + record.toString());
            }
//            System.out.println(records.count());

        }

    }

    static class TestConsumerRebalanceListener implements ConsumerRebalanceListener {

        //rebalance操作之前调用
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            System.out.println("onPartitionsRevoked>>>");
            for(TopicPartition t:partitions){
                System.out.println(t);
            }

        }

        //rebalance操作之后调用
        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            System.out.println("onPartitionsAssigned>>>");
            for(TopicPartition t:partitions){
                System.out.println(t);
            }
        }
    }
}
