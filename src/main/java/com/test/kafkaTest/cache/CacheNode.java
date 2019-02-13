package com.test.kafkaTest.cache;

import lombok.Data;

/**
 * Created by zhangfeng on 2018/9/27
 */
@Data
public class CacheNode<T> {

    private String key;

    private T value;

    private long expire;

    public CacheNode(){

    }

    public CacheNode(String key,T value){
        this.key = key;
        this.value = value;
        this.expire = 0L;
    }

    public CacheNode(String key,T value,long expire){
        this.key = key;
        this.value = value;
        this.expire = expire;
    }
}
