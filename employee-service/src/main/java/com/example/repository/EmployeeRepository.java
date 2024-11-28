package com.example.repository;

import com.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByPhoneNumber(String phoneNumber);

    List<Employee> findByIdOrNameContaining(Integer id, String name);

}
