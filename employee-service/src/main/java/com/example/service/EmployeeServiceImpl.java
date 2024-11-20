package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeHierarchyDTO;
import com.example.entity.*;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Employee createEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());

        Employee savedEmployee = employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository
                .findById(savedEmployee.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        List<Role> roleList = roleRepository.saveAll(employeeDTO.getRoles());
        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setEmployee(foundEmployee);
        roleMapping.setRoles(roleList);
        RoleMapping savedRoleMapping = roleMappingRepository.save(roleMapping);

//        List<Role> roleList = new ArrayList<>();
//        for(var r : employeeDTO.getRoles()) {
//           Role role= roleRepository.findById(r.getId())
//                   .orElseThrow(()->new RuntimeException("role not found"));
//           roleList.add(role);
//        }

        Salary salary = new Salary();
        salary.setEmployee(savedEmployee);
        salary.setYear(employeeDTO.getYear());
        salary.setSalary(employeeDTO.getSalary());
        salaryRepository.save(salary);

        Manager manager = new Manager();
        manager.setRoleMapping(savedRoleMapping);
        managerRepository.save(manager);

        return savedEmployee;
    }

    @Override
    public DetailsDTO getEmployee(Integer id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Salary salary = salaryRepository.findByEmployeeId(employee.getId());
        Manager manager = managerRepository.findByRoleMappingId(employee.getId());

        DetailsDTO detailsDTO = new DetailsDTO();
        detailsDTO.setId(employee.getId());
        detailsDTO.setName(employee.getName());
        detailsDTO.setPhoneNumber(employee.getPhoneNumber());
        detailsDTO.setJoiningDate(employee.getJoiningDate());
        detailsDTO.setSalary(salary.getSalary());
        detailsDTO.setRoles(manager.getRoleMapping().getRoles());
        detailsDTO.setEmployees(manager.getEmployees());

        return detailsDTO;
    }

    @Override
    public EmployeeHierarchyDTO getEmployeeHierarchy(Integer id) throws EmployeeNotFoundException {
        RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(id);

        Employee employee = roleMapping.getEmployee();
        Manager manager = managerRepository.findByEmployeesId(employee.getId());

        Salary salary = salaryRepository.findByEmployeeId(employee.getId());
        RoleMapping managerRoleMapping = roleMappingRepository.findByEmployeeId(employee.getId());

        EmployeeHierarchyDTO employeeHierarchyDTO = new EmployeeHierarchyDTO();
        employeeHierarchyDTO.setManagerId(employee.getId());
        employeeHierarchyDTO.setManagerName(employee.getName());
        employeeHierarchyDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeHierarchyDTO.setSalary(salary.getSalary());
        employeeHierarchyDTO.setJoiningDate(employee.getJoiningDate());
        employeeHierarchyDTO.setRoles(managerRoleMapping.getRoles());
        employeeHierarchyDTO.setEmployees(manager.getEmployees());
        return employeeHierarchyDTO;
    }

}
