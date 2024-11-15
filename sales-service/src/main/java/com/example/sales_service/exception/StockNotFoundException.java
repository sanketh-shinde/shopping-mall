package com.example.sales_service.exception;

public class StockNotFoundException extends RuntimeException{

    String message;


    public StockNotFoundException(String message)
    {
        super(message);
    }
}
