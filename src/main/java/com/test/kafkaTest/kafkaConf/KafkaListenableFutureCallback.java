package com.test.kafkaTest.kafkaConf;

import com.test.kafkaTest.cache.CacheNode;
import com.test.kafkaTest.cache.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by gexiaoshan on 2018/9/26.
 */
@Slf4j
@Component
public class KafkaListenableFutureCallback implements ListenableFutureCallback<SendResult<Object, Object>> {

//    @Resource(name = "REDIS_TEMPLATE")
    @Autowired(required = false)
    @Qualifier("REDIS_TEMPLATE")
    private RedisOperations redisOperations;
    @Autowired
    private RedisCallbackImpl redisCallback;

    LocalCache<String> localCache = new LocalCache<>(cacheNodes -> {
        AtomicBoolean result = new AtomicBoolean(true);

        /*cacheNodes.forEach(node -> {
            try {
                redisOperations.opsForValue().set(node.getKey(), node.getValue(), 7, TimeUnit.DAYS);
            } catch (Exception e) {
                result.set(false);
                log.error("offset to redis error:", e );
            }
        });*/

        try {
            //批量提交
            redisCallback.setCacheNodes(cacheNodes);
            redisOperations.executePipelined(redisCallback);
        } catch (Exception e) {
            result.set(false);
        }
        return result.get();
    });

    @Override
    public void onSuccess(SendResult<Object, Object> result) {
        String nowStr = DateUtil.localDateTimeToString(LocalDateTime.now(), "yyyyMMddHHmm");
        String key = KafkaUtil.redisOffsetKey(result.getRecordMetadata().topic(), nowStr, result.getRecordMetadata().partition());
        String offset = String.valueOf(result.getRecordMetadata().offset());
        localCache.set(key, offset, 60);
    }

    @Override
    public void onFailure(Throwable ex) {
        log.error("kafka sendMessage error, ex = {}", ex);
    }

    @Component
    class RedisCallbackImpl implements RedisCallback {

        List<CacheNode<String>> cacheNodes;

        @Override
        public Object doInRedis(RedisConnection connection) throws DataAccessException {
            cacheNodes.forEach(node -> {
                byte[] rawKey = redisOperations.getKeySerializer().serialize(node.getKey());
                byte[] rawValue = redisOperations.getValueSerializer().serialize(node.getValue());
                connection.setEx(rawKey, 7 * 24 * 60 * 60, rawValue);
            });
            return null;
        }

        public void setCacheNodes(List<CacheNode<String>> cacheNodes) {
            this.cacheNodes = cacheNodes;
        }
    }
}
