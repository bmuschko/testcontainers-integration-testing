package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.model.warehouse.Product;

public interface WarehouseService {
    void addProduct(Product product);
}
