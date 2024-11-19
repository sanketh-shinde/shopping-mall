package com.example.repository;

import com.example.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    Manager findByRoleMappingId(Integer id);

}
