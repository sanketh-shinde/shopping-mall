package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeHierarchyDTO;
import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;
import com.example.jwt.AuthRequest;
import com.example.jwt.AuthResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    Employee createEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    AuthResponse loginEmployee(AuthRequest authRequest) throws Exception;

    DetailsDTO getEmployee(Integer id) throws EmployeeNotFoundException;

    EmployeeHierarchyDTO getEmployeeHierarchy() throws EmployeeNotFoundException;

    List<Integer> getEmployeeManagerHierarchy(Integer id) throws EmployeeNotFoundException;

}
