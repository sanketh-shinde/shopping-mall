package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeHierarchyDTO;
import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;

public interface EmployeeService {

    Employee createEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;
    DetailsDTO getEmployee(Integer id) throws EmployeeNotFoundException;
    EmployeeHierarchyDTO getEmployeeHierarchy(Integer id) throws EmployeeNotFoundException;
}
