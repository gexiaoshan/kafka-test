package com.test.kafkaTest.kafkaConf;

/**
 * Created by gexiaoshan on 2018/10/8.
 */
public class KafkaUtil {
    private static final String KAFKA_REDIS = "kafka:%s:%s:%s";

    public static String redisOffsetKey(String topic, String time, int partition) {
        return String.format(KAFKA_REDIS, topic, time, partition);
    }
}
