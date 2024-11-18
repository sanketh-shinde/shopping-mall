package com.example.controller;

import com.example.entity.Salary;
import com.example.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PutMapping("/increment/{id}")
    public Salary incrementSalary(@PathVariable Integer id, @RequestBody Salary salary) {
        return salaryService.incrementSalary(id, salary);
    }

}
