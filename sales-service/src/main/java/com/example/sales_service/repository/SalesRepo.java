package com.example.sales_service.repository;

import com.example.sales_service.entities.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepo extends JpaRepository<Sales,Integer> {
}
