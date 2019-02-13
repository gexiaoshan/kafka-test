package com.test.kafkaTest.kafkaConf;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gexiaoshan on 2018/9/19.
 * 启用 kafka
 */
@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers:111.111.111.111:9092}")
    private String brokers;

    public Map<String, Object> initConfig() {
        Map<String, Object> properties = new HashMap<String, Object>();
        //kafka集群地址，ip+端口，以逗号隔开
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        //用来序列化key的Serializer接口的实现类.
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //用来序列化value的Serializer接口的实现类
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        return new DefaultKafkaProducerFactory(initConfig());
    }

}
