package com.example.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    private Integer id;
    private RoleMapping roleMapping;
    //private List<Employee> employees;

}
