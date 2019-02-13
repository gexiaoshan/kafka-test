package com.test.kafkaTest.kafkaConf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by gexiaoshan on 2018/9/26.
 */
@Component
public class KafKaTemplateExtend extends KafkaTemplate<Object, Object> {

    @Autowired
    private KafkaListenableFutureCallback kafkaListenableFutureCallback;

    public ListenableFuture<SendResult<Object, Object>> send(String topic, Object data) {
        ListenableFuture<SendResult<Object, Object>> future = super.send(topic, data);
        future.addCallback(kafkaListenableFutureCallback);
        return future;
    }

    @Autowired
    public KafKaTemplateExtend(ProducerFactory producerFactory) {
        super(producerFactory);
    }
}
