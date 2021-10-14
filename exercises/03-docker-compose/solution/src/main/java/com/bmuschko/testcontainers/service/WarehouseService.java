package com.bmuschko.testcontainers.service;

import java.math.BigDecimal;

public interface WarehouseService {
    void addProduct(String name, BigDecimal price, String category);
}
