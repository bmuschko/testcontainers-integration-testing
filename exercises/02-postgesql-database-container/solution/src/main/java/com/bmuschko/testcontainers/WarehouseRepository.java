package com.bmuschko.testcontainers;

public interface WarehouseRepository {
    void insertItem(Item item);
    Item getItem(Long id);
}
