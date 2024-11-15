package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployee(@PathVariable Integer id)  {
        return employeeService.getEmployee(id);
    }

}
