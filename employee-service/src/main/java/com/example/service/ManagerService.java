package com.example.service;

import com.example.dto.ManagerDTO;
import com.example.entity.Manager;
import com.example.exception.EmployeeNotFoundException;

public interface ManagerService {

    ManagerDTO assignManager(Integer empId, Integer managerId, Manager manager) throws EmployeeNotFoundException;

}
