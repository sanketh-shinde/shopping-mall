package com.example.sales_service.dto;

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
    private double salary;
    private LocalDate joiningDate;
    private int year;
    private List<Role> roles;
    private Manager manager;

}
