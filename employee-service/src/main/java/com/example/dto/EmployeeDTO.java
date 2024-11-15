package com.example.dto;

import com.example.entity.Manager;
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

    private Integer id;
    private String name;
    private String phoneNumber;
    private double salary;
    private LocalDate joiningDate;
    private int year;
    private Set<Role> roles;
    private Manager manager;

}
