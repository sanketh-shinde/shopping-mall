package com.example.controller;

import com.example.entity.Salary;
import com.example.response.ApiResponse;
import com.example.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PutMapping("/increment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Salary>> incrementSalary(
            @PathVariable Integer id,
            @RequestBody Salary salary
    ) {
        ApiResponse<Salary> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "salary incremented",
                salaryService.incrementSalary(id, salary)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
