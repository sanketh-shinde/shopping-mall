package com.example.repository;

import com.example.entity.RoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMappingRepository extends JpaRepository<RoleMapping, Integer> {

    RoleMapping findByEmployeeId(Integer id);

}
