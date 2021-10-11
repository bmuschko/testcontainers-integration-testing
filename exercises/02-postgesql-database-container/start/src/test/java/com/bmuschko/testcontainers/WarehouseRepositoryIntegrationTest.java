package com.bmuschko.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseRepositoryIntegrationTest {
    private WarehouseRepository warehouseRepository;

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
