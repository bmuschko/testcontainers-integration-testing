package com.bmuschko.testcontainers.repository.warehouse;

public interface WarehouseDatabaseRepository {
    void insertItem(Item item);
    Item getItem(Long id);
}
