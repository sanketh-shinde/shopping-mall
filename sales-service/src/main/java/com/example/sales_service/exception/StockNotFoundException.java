package com.example.sales_service.exception;

public class StockNotFoundException extends RuntimeException{


    public StockNotFoundException(String message)
    {
        super(message);
    }
}
