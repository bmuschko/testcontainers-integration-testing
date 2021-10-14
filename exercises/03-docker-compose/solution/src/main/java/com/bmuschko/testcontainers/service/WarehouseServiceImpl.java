package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.repository.warehouse.Item;
import com.bmuschko.testcontainers.repository.warehouse.Product;
import com.bmuschko.testcontainers.repository.warehouse.ProductIndexRepository;
import com.bmuschko.testcontainers.repository.warehouse.ProductIndexRepositoryImpl;
import com.bmuschko.testcontainers.repository.warehouse.UsernamePasswordCredentials;
import com.bmuschko.testcontainers.repository.warehouse.WarehouseDatabaseRepository;
import com.bmuschko.testcontainers.repository.warehouse.WarehouseDatabaseRepositoryImpl;

import java.math.BigDecimal;

public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseDatabaseRepository warehouseDatabaseRepository;
    private final ProductIndexRepository productIndexRepository;

    public WarehouseServiceImpl(String databaseUrl, UsernamePasswordCredentials databaseCredentials, String solrUrl) {
        warehouseDatabaseRepository = new WarehouseDatabaseRepositoryImpl(databaseUrl, databaseCredentials);
        productIndexRepository = new ProductIndexRepositoryImpl(solrUrl);
    }

    @Override
    public void addProduct(String name, BigDecimal price, String category) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        warehouseDatabaseRepository.insertItem(item);

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        productIndexRepository.insertProduct(product);
    }
}
