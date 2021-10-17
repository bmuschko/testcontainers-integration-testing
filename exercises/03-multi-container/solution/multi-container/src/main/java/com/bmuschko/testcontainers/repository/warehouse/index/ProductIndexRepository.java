package com.bmuschko.testcontainers.repository.warehouse.index;

import com.bmuschko.testcontainers.model.warehouse.Product;

public interface ProductIndexRepository {
    void insertProduct(Product product);
}
