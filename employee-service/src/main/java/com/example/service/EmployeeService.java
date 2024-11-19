package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;

public interface EmployeeService {

    Employee createEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;
    EmployeeDTO getEmployee(Integer id) throws EmployeeNotFoundException;
    void getEmployeeHierarchy(Integer id) throws EmployeeNotFoundException;
}
