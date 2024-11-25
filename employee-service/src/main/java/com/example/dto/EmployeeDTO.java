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
public class EmployeeDTO {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String password;
    private double salary;
    private LocalDate joiningDate;
    private int year;
    private List<Role> roles;

}
