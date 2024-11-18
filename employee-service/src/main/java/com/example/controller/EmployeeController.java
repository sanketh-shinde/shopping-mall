package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.response.CommonResponse;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public CommonResponse createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        CommonResponse response = new CommonResponse();
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("employee created");
        response.setResult(employeeService.createEmployee(employeeDTO));
        return response;
    }

    @GetMapping("/get/{id}")
    public CommonResponse getEmployee(@PathVariable Integer id)  {
        CommonResponse response = new CommonResponse();
        response.setStatus(HttpStatus.OK);
        response.setMessage("employee fetched");
        response.setResult(employeeService.getEmployee(id));
        return response;
    }

}
