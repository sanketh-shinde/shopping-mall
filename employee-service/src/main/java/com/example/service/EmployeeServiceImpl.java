package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMappingRepository roleMappingRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());

        Employee savedEmployee = employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository
                .findById(savedEmployee.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Role role = roleRepository.findById(employeeDTO.getManager().getRoleMapping().getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setEmployee(foundEmployee);
        roleMapping.setRole(role);
        RoleMapping savedRoleMapping = roleMappingRepository.save(roleMapping);

        Manager manager = new Manager();
        manager.setRoleMapping(savedRoleMapping);
        manager.setEmployees(employeeDTO.getManager().getEmployees());
        managerRepository.save(manager);

        Salary salary = new Salary();
        salary.setEmployee(savedEmployee);
        salary.setSalary(employeeDTO.getSalary());
        salary.setYear(employeeDTO.getYear());
        salaryRepository.save(salary);

        return savedEmployee;
    }

    @Override
    public EmployeeDTO getEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Salary salary = salaryRepository.findByEmployeeId(employee.getId());
        Manager manger = managerRepository.findByRoleMappingId(employee.getId());

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setJoiningDate(employee.getJoiningDate());

        employeeDTO.setYear(salary.getYear());
        employeeDTO.setSalary(salary.getSalary());

        employeeDTO.setManager(manger);
        employeeDTO.setRoles(Set.of(manger.getRoleMapping().getRole()));

        return employeeDTO;
    }

}
