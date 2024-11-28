package com.example.controller;

import com.example.dto.DetailsDTO;
import com.example.dto.ManagerDTO;
import com.example.entity.Manager;
import com.example.exception.EmployeeNotFoundException;
import com.example.response.ApiResponse;
import com.example.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PutMapping("/assignManager/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<ManagerDTO>> assignManager(
            @PathVariable Integer id,
            @RequestBody Manager manager
    ) throws EmployeeNotFoundException {
        ApiResponse<ManagerDTO> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "manager assigned successfully",
                managerService.assignManager(id, manager)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DetailsDTO>>> findManager(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name) throws EmployeeNotFoundException {
        ApiResponse<List<DetailsDTO>> apiResponse = new ApiResponse<>(
                HttpStatus.OK,
                "manager fetched",
                managerService.findManagerByIdOrName(id, name)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
