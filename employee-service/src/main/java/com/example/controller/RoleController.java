package com.example.controller;

import com.example.entity.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Role createRoles(@RequestBody Role role) {
        return roleService.createRole(role);
    }

}
