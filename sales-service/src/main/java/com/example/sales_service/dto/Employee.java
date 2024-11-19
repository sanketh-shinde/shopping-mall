package com.example.sales_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Employee {

    private Integer id;
    private String name;
    private String phoneNumber;
    private double salary;
    private LocalDate joiningDate;
    private Set<Role> roles;
    private Manager manager;

}
