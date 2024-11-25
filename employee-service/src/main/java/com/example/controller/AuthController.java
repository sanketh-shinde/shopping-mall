package com.example.controller;

import com.example.jwt.AuthRequest;
import com.example.jwt.AuthResponse;
import com.example.response.ApiResponse;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginEmployee(@RequestBody AuthRequest authRequest)
            throws Exception {
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Authentication Successful",
                employeeService.loginEmployee(authRequest)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
