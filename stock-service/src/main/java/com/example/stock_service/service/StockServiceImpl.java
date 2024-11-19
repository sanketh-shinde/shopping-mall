package com.example.stock_service.service;

import com.example.stock_service.entity.Stock;
import com.example.stock_service.exception.StockNotFoundException;
import com.example.stock_service.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    //SERVICE LOGIC TO CREATE STOCK
    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    //SERVICE LOGIC TO FIND STOCK BY ID
    @Override
    public Stock findStockById(int id) {
        return stockRepository.findById(id).
                orElseThrow(() -> new StockNotFoundException("Stock is not found with the given id: " + id));
    }

    //SERVICE LOGIC FOR FIND ALL STOCK
    @Override
    public List<Stock> findAllStock() {
        return stockRepository.findAll();
    }

    //SERVICE LOGIC TO UPDATE THE PRICE OF PRODUCT
    @Override
    public Stock updatePrice(int id, double price) {
        Stock oldStock = stockRepository.findById(id).
                orElseThrow(() -> new StockNotFoundException("Stock is not found with the given id: " + id));
        if (oldStock != null) {
            oldStock.setPrice(price);
            stockRepository.save(oldStock);
        }
        return oldStock;
    }

    //SERVICE LOGIC TO DELETE STOCK BY ID
    @Override
    public void deleteStock(int id) {
        Stock stock = stockRepository.findById(id).
                orElseThrow(() -> new StockNotFoundException("Stock is not found with the given id: " + id));
        stockRepository.delete(stock);
    }

    //SERVICE LOGIC TO ADD NEW STOCK AS A LIST
    @Transactional
    @Override
    public List<Stock> addNewStock(List<Stock> stocks) {

        //optimized way

        // Fetch all existing stocks once and map them by category + productName for faster lookup
        List<Stock> oldStocks = stockRepository.findAll();
        Map<String, Stock> stockMap = oldStocks.stream()
                .collect(Collectors.toMap(
                        stock -> stock.getCategory().toLowerCase() + stock.getProductName().toLowerCase(),
                        stock -> stock
                ));

        // Prepare a list to collect stocks that need to be saved
        List<Stock> stocksToSave = new ArrayList<>();

        for (Stock newStock : stocks) {
            String key = newStock.getCategory().toLowerCase() + newStock.getProductName().toLowerCase();

            // Check if the stock already exists in the map
            if (stockMap.containsKey(key)) {
                Stock existingStock = stockMap.get(key);
                existingStock.setQuantity(existingStock.getQuantity() + newStock.getQuantity());
                stocksToSave.add(existingStock);
            } else {
                // If it's a new stock, add it to the map and to the list to save
                stockMap.put(key, newStock);
                stocksToSave.add(newStock);
            }
        }
        // Save all updated and new stocks in a single batch
        stockRepository.saveAll(stocksToSave);
        return stockRepository.findAll();
    }

    @Override
    public List<Stock> findStockByCategory(String category) {
        return stockRepository.findByCategory(category);

    }

    @Override
    public int getStockQuantity(int id) {
     return   stockRepository.getStockQuantity(id);


    }

    @Transactional
    @Override
    public List<Stock> bulkUpdateStockPrice(List<Stock> stocks) {
        return stocks.stream()
                .map(stock -> {
                    Stock existingStock = stockRepository.findById(stock.getId())
                            .orElseThrow(() -> new StockNotFoundException("Stock not found with ID: " + stock.getId()));
                    existingStock.setPrice(stock.getPrice());
                    return stockRepository.save(existingStock);
                })
                .toList();
    }

    @Override
    public List<Stock> findStockByPriceRange(double minPrice, double maxPrice) {
        return stockRepository.findStockByPriceRange(minPrice , maxPrice);
    }

    @Override
    public Stock findStockByProductName(String productName) {
        return stockRepository.findByProductName(productName);
    }

    @Transactional
    @Override
    public Stock updateQuantityAfterSell(int sellQuantity, int stockId) {
        Stock stockById = findStockById(stockId);
        int quantity = stockById.getQuantity();
        stockById.setQuantity(quantity - sellQuantity);
        return stockRepository.save(stockById);
    }


}
