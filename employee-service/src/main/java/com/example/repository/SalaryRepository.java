package com.example.repository;

import com.example.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    Salary findByEmployeeId(Integer employeeId);

}
