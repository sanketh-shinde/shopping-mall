package com.example.stock_service.exception;

public class StockNotFoundException extends RuntimeException{
   public StockNotFoundException(String message){
       super(message);
   }
}
