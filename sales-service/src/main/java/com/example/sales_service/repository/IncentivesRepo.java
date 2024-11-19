package com.example.sales_service.repository;

import com.example.sales_service.entities.Incentives;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncentivesRepo extends JpaRepository<Incentives,Integer> {

}
