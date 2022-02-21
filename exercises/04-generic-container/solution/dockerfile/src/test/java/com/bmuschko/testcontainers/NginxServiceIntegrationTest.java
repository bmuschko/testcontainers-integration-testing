package com.bmuschko.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Testcontainers
public class NginxServiceIntegrationTest {
    private NginxService nginxService;

    @Container
    private final GenericContainer nginx = new GenericContainer(new ImageFromDockerfile().withDockerfile(new File("src/test/resources/Dockerfile").toPath())).withExposedPorts(80);

    @BeforeEach
    public void setUp() {
        String host = nginx.getHost();
        Integer port = nginx.getFirstMappedPort();
        String endpointUrl = "http://" + host + ":" + port;
        nginxService = new NginxServiceImpl(endpointUrl);
    }

    @Test
    public void testPing() {
        nginxService.ping();
    }
}
