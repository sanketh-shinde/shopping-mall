package com.example.service;

import com.example.entity.Salary;

import java.util.List;

public interface SalaryService {

    List<Salary> incrementSalaryAll();

    Salary incrementSalary(Integer id,Salary salary);

}
