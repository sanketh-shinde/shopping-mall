package com.example.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    private Integer id;
    private int year;
    private double salary;
    private Employee employee;

}
