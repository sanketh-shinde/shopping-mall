package com.example.dto;

import com.example.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHierarchyDTO {

    private Integer managerId;
    private String managerName;
    private String phoneNumber;
    private double salary;
    private LocalDate joiningDate;
    private List<Role> roles;
    private List<EmployeeHierarchyDTO> employees;

}
