package com.example.dto;

import com.example.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String name;
    private String phoneNumber;
    private double salary;
    private LocalDate joiningDate;
    private Set<Role> roles;

}
