package com.bmuschko.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class RedisRepositoryIntegrationTest {
    private RedisRepository redisRepository;

    @Container
    private final GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.6-alpine")).withExposedPorts(6379);

    @BeforeEach
    public void setUp() {
        String host = redis.getHost();
        Integer port = redis.getFirstMappedPort();
        redisRepository = new RedisRepository(host, port);
    }

    @Test
    public void testSimplePutAndGet() {
        String key = "test";
        String value = "example";

        redisRepository.put(key, value);

        String retrieved = redisRepository.get(key);
        assertEquals(value, retrieved);
    }
}
