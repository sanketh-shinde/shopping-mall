package com.example.controller;

import com.example.dto.DetailsDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeHierarchyDTO;
import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;
import com.example.response.ApiResponse;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody EmployeeDTO employeeDTO)
            throws EmployeeNotFoundException {
        ApiResponse<Employee> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED,
                "employee created",
                employeeService.createEmployee(employeeDTO)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    public ResponseEntity<ApiResponse<DetailsDTO>> getEmployee(@PathVariable Integer id)
            throws EmployeeNotFoundException {
        ApiResponse<DetailsDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "employee found",
                employeeService.getEmployee(id)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/hierarchy/{id}")
    public ResponseEntity<ApiResponse<List<Integer>>> getEmployeeManagerHierarchy(
            @PathVariable Integer id
    ) throws EmployeeNotFoundException {
        ApiResponse<List<Integer>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Hierarchy Found",
                employeeService.getEmployeeManagerHierarchy(id)
        );
       return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<EmployeeHierarchyDTO>> getEmployeeHierarchy() throws EmployeeNotFoundException {
        ApiResponse<EmployeeHierarchyDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "Hierarchy Found",
                employeeService.getEmployeeHierarchy()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
