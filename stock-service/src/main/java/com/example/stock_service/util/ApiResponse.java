package com.example.stock_service.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;


}