package com.bmuschko.testcontainers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class RedisRepositoryIntegrationTest {
    private RedisRepository redisRepository;

    @Test
    public void testSimplePutAndGet() {
        String key = "test";
        String value = "example";

        redisRepository.put(key, value);

        String retrieved = redisRepository.get(key);
        assertEquals(value, retrieved);
    }
}
