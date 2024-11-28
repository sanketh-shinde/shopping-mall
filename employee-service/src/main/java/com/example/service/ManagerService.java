package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.ManagerDTO;
import com.example.entity.Manager;
import com.example.exception.EmployeeNotFoundException;

import java.util.List;

public interface ManagerService {

    ManagerDTO assignManager(Integer id, Manager manager)
            throws EmployeeNotFoundException;

    List<DetailsDTO> findManagerByIdOrName(Integer id, String name)
            throws EmployeeNotFoundException;

}
