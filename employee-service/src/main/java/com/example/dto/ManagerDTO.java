package com.example.dto;

import com.example.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {

    private Integer id;
    private List<Employee> employees;

}
