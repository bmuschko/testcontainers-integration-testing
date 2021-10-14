package com.bmuschko.testcontainers.repository.warehouse;

public class ProductIndexException extends RuntimeException {
    public ProductIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
