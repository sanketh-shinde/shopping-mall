package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.entity.Manager;
import com.example.entity.RoleMapping;
import com.example.entity.Salary;
import com.example.repository.EmployeeRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.RoleMappingRepository;
import com.example.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private RoleMappingRepository roleMappingRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public EmployeeDTO assignManager(Integer empId, Integer managerId, Manager manager) {
        Employee employee =  employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Manager fetchedManager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Salary salary = salaryRepository.findByEmployeeId(employee.getId());

        RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(employee.getId());

        List<Employee> employeeList = fetchedManager.getEmployees();
        employeeList.addAll(manager.getEmployees());
        manager.setId(fetchedManager.getId());
        manager.setRoleMapping(fetchedManager.getRoleMapping());
        manager.setEmployees(employeeList);
        managerRepository.save(manager);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setRoles(Set.of(roleMapping.getRole()));
        employeeDTO.setYear(salary.getYear());
        employeeDTO.setSalary(salary.getSalary());

        return employeeDTO;
    }

}
