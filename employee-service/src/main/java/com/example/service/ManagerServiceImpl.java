package com.example.service;

import com.example.dto.ManagerDTO;
import com.example.entity.Employee;
import com.example.entity.Manager;
import com.example.entity.RoleMapping;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.RoleMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private RoleMappingRepository roleMappingRepository;

    @Override
    public ManagerDTO assignManager(Integer id, Manager manager)
            throws EmployeeNotFoundException {

        RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(id);
        Manager managerObj = managerRepository.findByRoleMappingId(roleMapping.getId());

        List<Employee> employeesToAdd = manager.getEmployees();

        for (Employee employee : employeesToAdd) {
            employeeRepository.findById(id)
                    .orElseThrow(() ->
                            new EmployeeNotFoundException(
                                    "Cannot assign manager to this employee " +
                                            employee.getId() + " because employee does not exists"
                            ));
        }

        List<Employee> employeeList = managerObj.getEmployees();
        for (Employee emp : employeeList) {
            employeesToAdd.removeIf(item -> emp.getId().equals(item.getId()));
        }
        employeeList.addAll(employeesToAdd);
        managerObj.setEmployees(employeeList);

        Manager savedManager = managerRepository.save(managerObj);

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(savedManager.getId());
        managerDTO.setName(roleMapping.getEmployee().getName());
        managerDTO.setEmployees(savedManager.getEmployees());

        return managerDTO;
    }

}
