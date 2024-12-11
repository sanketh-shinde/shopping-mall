package com.example.stock_service.repository;

import com.example.stock_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<List<Stock>> findByCategory(String category);

    // Custom query to get stock quantity by id
    @Query("select s.quantity from Stock s where s.id = :id")
    int getStockQuantity(@Param("id") int id);

    @Query("select s from Stock s where s.price between :minPrice and :maxPrice")
    List<Stock> findStockByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    Stock findByProductName(String productName);
}
