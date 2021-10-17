package com.bmuschko.testcontainers.repository.warehouse.db;

public class WarehouseDatabaseException extends RuntimeException {
    public WarehouseDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
