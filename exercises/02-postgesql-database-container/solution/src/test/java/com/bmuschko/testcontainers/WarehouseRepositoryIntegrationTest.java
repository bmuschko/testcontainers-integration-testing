package com.bmuschko.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class WarehouseRepositoryIntegrationTest {
    private WarehouseRepository warehouseRepository;

    @Container
    private final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:9.6.12")
            .withInitScript("warehouse.sql")
            .withDatabaseName("warehouse");

    @BeforeEach
    public void setUp() {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        warehouseRepository = new WarehouseRepositoryImpl(jdbcUrl, username, password);
    }

    @Test
    public void testInsertAndGetItem() {
        Item clock = new Item();
        clock.setName("Clock");
        clock.setPrice(new BigDecimal(39.99));

        warehouseRepository.insertItem(clock);

        Item retrieved = warehouseRepository.getItem(clock.getId());
        assertEquals(clock, retrieved);
    }
}
