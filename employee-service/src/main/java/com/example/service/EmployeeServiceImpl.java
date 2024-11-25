package com.example.service;

import com.example.dto.DetailsDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeHierarchyDTO;
import com.example.entity.*;
import com.example.exception.EmployeeNotFoundException;
import com.example.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@Slf4j
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

        return detailsDTO;
    }



    @Override
    public EmployeeHierarchyDTO getEmployeeHierarchy() throws EmployeeNotFoundException {
        Integer employeeId = 1;
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Fetch the relevant details for the employee
        EmployeeHierarchyDTO employeeHierarchyDTO = createEmployeeHierarchyDTO(employee);

        // Initialize visited set to track circular references
        Set<Integer> visited = new HashSet<>();

        // Build the hierarchy for the manager's employees
        RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(employee.getId());
        Manager manager = managerRepository.findByRoleMappingId(roleMapping.getId());
        if (manager != null) {
            employeeHierarchyDTO.setEmployees(buildHierarchy(manager.getEmployees(), visited));
        } else {
            employeeHierarchyDTO.setEmployees(Collections.emptyList()); // No subordinates
        }
        return employeeHierarchyDTO;
    }

    private EmployeeHierarchyDTO createEmployeeHierarchyDTO(Employee employee) {
        Salary salary = salaryRepository.findByEmployeeId(employee.getId());
        RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(employee.getId());

        EmployeeHierarchyDTO dto = new EmployeeHierarchyDTO();
        dto.setManagerId(employee.getId());
        dto.setManagerName(employee.getName());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setSalary(salary.getSalary());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setRoles(roleMapping.getRoles());
        return dto;
    }

    private List<EmployeeHierarchyDTO> buildHierarchy(List<Employee> employees, Set<Integer> visited) {
        List<EmployeeHierarchyDTO> result = new ArrayList<>();

        for (Employee employee : employees) {
            // Prevent circular references by checking if the employee has already been visited
            if (visited.contains(employee.getId())) {
                continue; // Skip this employee
            }

            // Add the current employee to the visited set
            visited.add(employee.getId());

            // Fetch employee details and create the DTO
            EmployeeHierarchyDTO dto = createEmployeeHierarchyDTO(employee);

            // Recursively build the hierarchy for subordinates
            RoleMapping roleMapping = roleMappingRepository.findByEmployeeId(employee.getId());
            Manager manager = managerRepository.findByRoleMappingId(roleMapping.getId());

            if (manager != null && manager.getEmployees() != null && !manager.getEmployees().isEmpty()) {
                dto.setEmployees(buildHierarchy(manager.getEmployees(), visited)); // Recursive call
            } else {
                dto.setEmployees(Collections.emptyList()); // No subordinates
            }

            result.add(dto);
        }
        return result;
    }

    @Override
    public List<Integer> getEmployeeManagerHierarchy(Integer id) throws EmployeeNotFoundException {
        Set<Integer> visited = new HashSet<>();
        List<Integer> hierarchyList = new ArrayList<>();
        fetchEmployeeHierarchy(id, hierarchyList, visited);
        log.info("Final Hierarchy List: {}", hierarchyList);
        return hierarchyList;
    }

    private void fetchEmployeeHierarchy(
            Integer employeeId,
            List<Integer> hierarchyList,
            Set<Integer> visited
    )
            throws EmployeeNotFoundException {

        if (visited.contains(employeeId)) {
            throw new IllegalStateException(
                    "Circular dependency detected in employee hierarchy for Employee ID: " + employeeId
            );
        }

        visited.add(employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee is not found with id " + employeeId)
                );

        hierarchyList.add(employee.getId());

        List<Role> roles = roleMappingRepository.findByEmployeeId(employee.getId()).getRoles();
        log.info("Roles for Employee ID {}: {}", employeeId, roles);

        for (Role role : roles) {
            if (role.getDesignation().contains("Sales Supervisor")) {
                log.info("Reached Sale Supervisor with ID: {}", employee.getId());
                return;
            }
        }

        Manager manager = managerRepository.findByEmployeesId(employee.getId());
        log.info("Manager is : {}", manager);

        if (manager != null) {
            Manager byRoleMappingId = managerRepository.findByRoleMappingId(manager.getRoleMapping().getId());
            RoleMapping roleMapping = roleMappingRepository.findById(byRoleMappingId.getId())
                    .orElseThrow(() -> new RuntimeException("role mapping not found"));
            fetchEmployeeHierarchy(roleMapping.getEmployee().getId(), hierarchyList, visited);
        } else {
            log.warn("No manager found for Employee ID: {}", employeeId);
        }
    }

}
