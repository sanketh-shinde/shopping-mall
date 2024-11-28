package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.ManagerDTO;
import com.example.entity.Employee;
import com.example.entity.Manager;
import com.example.entity.RoleMapping;
import com.example.entity.Salary;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.RoleMappingRepository;
import com.example.repository.SalaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private SalaryRepository salaryRepository;

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

    @Override
    public List<DetailsDTO> findManagerByIdOrName(Integer id, String name)
            throws EmployeeNotFoundException {
        List<DetailsDTO> managerList = new ArrayList<>();

        List<Employee> employeeList = employeeRepository.findByIdOrNameContaining(id, name);
        for (Employee employee : employeeList) {

            Salary salary = salaryRepository.findByEmployeeId(employee.getId());
            RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(employee.getId());
            Manager manager = managerRepository.findByRoleMappingId(roleMapping.getId());

            DetailsDTO detailsDTO = new DetailsDTO();
            detailsDTO.setId(employee.getId());
            detailsDTO.setName(employee.getName());
            detailsDTO.setPhoneNumber(employee.getPhoneNumber());
            detailsDTO.setJoiningDate(employee.getJoiningDate());
            detailsDTO.setSalary(salary.getSalary());
            detailsDTO.setRoles(manager.getRoleMapping().getRoles());
            detailsDTO.setEmployees(manager.getEmployees());

            managerList.add(detailsDTO);
        }
        return managerList;
    }

}
