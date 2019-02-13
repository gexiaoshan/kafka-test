package com.test.kafkaTest.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by gexiaoshan on 2019/2/13.
 * spring与kafka结合 消费消息
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"test1"})
    public void receive(String message){
        System.out.println("test1--消费消息:" + message);
    }
}
