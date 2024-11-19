package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;

public interface EmployeeService {

    Employee createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployee(Integer id);
}
