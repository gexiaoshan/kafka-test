package com.test.kafkaTest.kafkaConf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gexiaoshan on 2018/10/8.
 */
@Component
@Slf4j
public class KafkaResetOffset {

//    @Resource(name = "REDIS_TEMPLATE")
    @Autowired(required = false)
    @Qualifier("REDIS_TEMPLATE")
    private RedisOperations redisOperations;
    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;
    @Value("${spring.kafka.bootstrap-servers:111.111.111.111:9092}")
    private String brokers;
    @Value("${spring.kafka.resetOffset.timeInterver:60}")
    private Integer interver;

    private Map<String, Object> initConfig() {
        Map<String, Object> properties = new HashMap<String, Object>();
        //kafka集群地址，ip+端口，以逗号隔开
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        //用来序列化key的Serializer接口的实现类.
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //用来序列化value的Serializer接口的实现类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    //重置offset
    public void resetOffset(String topic, String groupName, String time) {
        Map<String, Object> properties = initConfig();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        KafkaConsumer consumer = new KafkaConsumer<>(properties);
        //取该topic下的分区
        List<PartitionInfo> list = kafkaTemplate.partitionsFor(topic);
        for (PartitionInfo p : list) {
            //从redis中取某分区的offset
            String offset = getOffset(topic, p.partition(), time);
            if (StringUtils.isNotEmpty(offset)) {
                log.info("重置offset，topic:{},groupName:{},offset:{}", topic, groupName, offset);
                TopicPartition tp = new TopicPartition(topic, p.partition());
                Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                offsets.put(tp, new OffsetAndMetadata(Long.valueOf(offset), "reset"));
                consumer.commitSync(offsets);
            }
        }
    }

    //从redis取offset
    private String getOffset(String topic, int partition, String time) {
        String key;
        String offset = null;
        for (int i = 0; i < interver; i++) {
            key = KafkaUtil.redisOffsetKey(topic, time, partition);
            offset = (String) redisOperations.opsForValue().get(key);
            if (StringUtils.isNotEmpty(offset)) {
                return offset;
            } else {
                LocalDateTime dateTime = DateUtil.stringToLocalDateTime(time, "yyyyMMddHHmm");
                time = DateUtil.localDateTimeToString(dateTime.minus(1, ChronoUnit.MINUTES), "yyyyMMddHHmm");
            }
        }
        return offset;
    }
}
