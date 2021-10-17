package com.bmuschko.testcontainers.repository.warehouse.db;

import com.bmuschko.testcontainers.model.warehouse.Product;

public interface WarehouseDatabaseRepository {
    void insertProduct(Product product);
    Product getProduct(Long id);
}
