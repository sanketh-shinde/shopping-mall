package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Manager;
import com.example.exception.EmployeeNotFoundException;

public interface ManagerService {

    EmployeeDTO assignManager(Integer empId, Integer managerId, Manager manager) throws EmployeeNotFoundException;

}
