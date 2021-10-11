package com.bmuschko.testcontainers;

import redis.clients.jedis.Jedis;

public class RedisRepository {
    private final Jedis jedis;

    public RedisRepository(String host, Integer port) {
        this.jedis = new Jedis(host, port);
    }

    public void put(String key, String value) {
        jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }
}
