package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.model.warehouse.Product;
import com.bmuschko.testcontainers.repository.warehouse.db.UsernamePasswordCredentials;
import com.bmuschko.testcontainers.repository.warehouse.db.WarehouseDatabaseRepository;
import com.bmuschko.testcontainers.repository.warehouse.db.WarehouseDatabaseRepositoryImpl;
import com.bmuschko.testcontainers.repository.warehouse.index.ProductIndexRepository;
import com.bmuschko.testcontainers.repository.warehouse.index.ProductIndexRepositoryImpl;

public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseDatabaseRepository warehouseDatabaseRepository;
    private final ProductIndexRepository productIndexRepository;

    public WarehouseServiceImpl(String databaseUrl, UsernamePasswordCredentials databaseCredentials, String solrUrl) {
        warehouseDatabaseRepository = new WarehouseDatabaseRepositoryImpl(databaseUrl, databaseCredentials);
        productIndexRepository = new ProductIndexRepositoryImpl(solrUrl);
    }

    @Override
    public void addProduct(Product product) {
        warehouseDatabaseRepository.insertProduct(product);
        productIndexRepository.insertProduct(product);
    }
}
