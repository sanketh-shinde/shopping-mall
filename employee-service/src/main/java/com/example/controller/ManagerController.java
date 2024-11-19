package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.entity.Manager;
import com.example.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PutMapping("/assignManager")
    public EmployeeDTO assignManager(@RequestParam Integer empId, @RequestParam Integer managerId, @RequestBody Manager manager) {
        return managerService.assignManager(empId, managerId, manager);
    }

}
