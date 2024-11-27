package com.example.sales_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<String> stockNotFoundException(StockNotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }


    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String>  employeeNoFoundException(EmployeeNotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> insufficientStockException(InsufficientStockException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
