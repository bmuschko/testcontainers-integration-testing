package com.bmuschko.testcontainers;

public class WarehouseException extends RuntimeException {
    public WarehouseException(String message, Throwable cause) {
        super(message, cause);
    }
}
