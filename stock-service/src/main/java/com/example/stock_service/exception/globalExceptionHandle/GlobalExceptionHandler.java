//package com.example.stock_service.exception.globalExceptionHandle;
//import com.example.stock_service.exception.StockNotFoundException;
//import com.example.stock_service.util.ApiResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    //Handle Stock
//    @ExceptionHandler(StockNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<?> stockNotFoundException(StockNotFoundException ex){
//        ErrorDetails errorDetails = new ErrorDetails(
//                HttpStatus.NOT_FOUND.value(),
//                ex.getMessage()
//
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//
//    }
//
//    // Generic exception handler for all other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex) {
//
//        ErrorDetails errorDetails = new ErrorDetails(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "An unexpected error occurred: " + ex.getMessage()
//
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> fieldErrors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                fieldErrors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        ApiResponse<Object> response = new ApiResponse<>(
//                "ERROR",
//                "Validation failed",
//                null,
//                Map.of("fieldErrors", fieldErrors)
//        );
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse<Object>> handleGenericException(RuntimeException ex) {
//        ApiResponse<Object> response = new ApiResponse<>(
//                "ERROR",
//                ex.getMessage(),
//                null,
//                Map.of("error","error")
//        );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
//}

package com.example.stock_service.exception.globalExceptionHandle;

import com.example.stock_service.exception.StockNotFoundException;
import com.example.stock_service.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle StockNotFoundException
    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Object>> handleStockNotFoundException(StockNotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "ERROR",
                ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle validation exceptions (e.g., @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Object> response = new ApiResponse<>(
                "ERROR",
                "Validation failed",
                null,
                fieldErrors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle generic RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "ERROR",
                "An unexpected error occurred: " + ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle any other uncaught exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "ERROR",
                "An unexpected error occurred: " + ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
