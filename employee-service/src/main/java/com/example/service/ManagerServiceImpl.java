package com.example.service;

import com.example.dto.ManagerDTO;
import com.example.entity.Employee;
import com.example.entity.Manager;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public ManagerDTO assignManager(Integer empId, Integer managerId, Manager manager)
            throws EmployeeNotFoundException {
        Employee employee =  employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Manager fetchedManager = managerRepository.findById(managerId)
                .orElseThrow(() -> new EmployeeNotFoundException("Manager not found"));

        List<Employee> employeeList = fetchedManager.getEmployees();
        employeeList.addAll(manager.getEmployees());
        manager.setId(fetchedManager.getId());
        manager.setRoleMapping(fetchedManager.getRoleMapping());
        manager.setEmployees(employeeList);
        Manager savedManager = managerRepository.save(manager);

        System.out.println(savedManager.getEmployees());
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(savedManager.getId());
        managerDTO.setEmployees(savedManager.getEmployees());

        return managerDTO;
    }

}
