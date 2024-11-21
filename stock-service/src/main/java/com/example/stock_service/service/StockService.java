package com.example.stock_service.service;

import com.example.stock_service.entity.Stock;

import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);
    Stock findStockById(int id);
    List<Stock> findAllStock();
    Stock updatePrice(int id,double price);
    void deleteStock(int id);
    List<Stock> addNewStock(List<Stock> stocks);

    List<Stock> findStockByCategory(String category);

    int getStockQuantity(int id);

    List<Stock> bulkUpdateStockPrice(List<Stock> stocks);

    List<Stock> findStockByPriceRange(double minPrice, double maxPrice);

    Stock findStockByProductName(String dressName);

    Stock updateQuantityAfterSell(int sellQuantity, int stockId);
}
