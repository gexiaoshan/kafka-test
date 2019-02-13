package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by gexiaoshan on 2018/9/29.
 * 消息发送拦截器
 */
public class ProducerInterceptorImpl implements ProducerInterceptor {

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        System.out.println("interceptor:"+record.topic());
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
