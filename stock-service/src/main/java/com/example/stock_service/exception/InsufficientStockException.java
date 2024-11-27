package com.example.stock_service.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String insufficientStock) {
        super(insufficientStock);
    }
}
