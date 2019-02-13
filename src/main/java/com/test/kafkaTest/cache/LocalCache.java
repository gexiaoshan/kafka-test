package com.test.kafkaTest.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * Created by zhangfeng on 2018/9/27
 */
public class LocalCache<T> {
    private static ScheduledExecutorService expiredPool = new ScheduledThreadPoolExecutor(5);

    private ReentrantLock lock = new ReentrantLock();

    private Map<String, CacheNode<T>> cache = new ConcurrentHashMap<>();

    private Function<List<CacheNode<T>>, Boolean> expiredCallback;

    public LocalCache() {
    }

    public LocalCache(Boolean enableExpire) {
        if (enableExpire)
            expiredPool.scheduleWithFixedDelay(new ExpiredNodeWork(), 0, 60, TimeUnit.SECONDS);
    }

    public LocalCache(Function<List<CacheNode<T>>, Boolean> expiredCallback) {
        this.expiredCallback = expiredCallback;
        expiredPool.scheduleWithFixedDelay(new ExpiredNodeWork(), 0, 60, TimeUnit.SECONDS);
    }

    public CacheNode<T> get(String key) {
        return cache.get(key);
    }

    public void set(String key, T value) {
        cache.put(key, new CacheNode(key, value));
    }

    public void set(String key, T value, int ttl) {
        cache.put(key, new CacheNode(key, value, System.currentTimeMillis() + ttl * 1000));
    }

    public CacheNode<T> remove(String key) {
        return cache.remove(key);
    }

    private class ExpiredNodeWork implements Runnable {

        @Override
        public void run() {
            long now = System.currentTimeMillis();
//            while (true) {
                lock.lock();
                try {
                    if (expiredCallback == null) {
                        Iterator<Map.Entry<String, CacheNode<T>>> iterator = cache.entrySet().iterator();
                        while (iterator.hasNext()) {
                            CacheNode<T> cacheNode = iterator.next().getValue();
                            if (cacheNode.getExpire() != 0L && cacheNode.getExpire() < now) {
                                remove(cacheNode.getKey());
                            }
                        }
                    } else {
                        List<CacheNode<T>> expiredNodes = new ArrayList();
                        Iterator<Map.Entry<String, CacheNode<T>>> iterator = cache.entrySet().iterator();
                        while (iterator.hasNext()) {
                            CacheNode<T> cacheNode = iterator.next().getValue();
                            if (cacheNode.getExpire() != 0L && cacheNode.getExpire() < now) {
                                expiredNodes.add(cacheNode);
                            }
                        }
                        if(expiredNodes.size() > 0){
                            boolean result = expiredCallback.apply(expiredNodes);
                            if (result) {
                                expiredNodes.forEach(node -> remove(node.getKey()));
                            }
                        }
                    }
                } finally {
                    lock.unlock();
                }
//            }
        }
    }
}