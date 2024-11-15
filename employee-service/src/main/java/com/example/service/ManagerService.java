package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Manager;

public interface ManagerService {

    EmployeeDTO assignManager(Integer empId, Integer managerId, Manager manager);

}
