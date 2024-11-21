package com.example.controller;

import com.example.entity.Role;
import com.example.response.ApiResponse;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Role>> createRoles(@RequestBody Role role) {
        ApiResponse<Role> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED,
                "role created",
                roleService.createRole(role)
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
