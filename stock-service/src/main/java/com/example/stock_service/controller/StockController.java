package com.example.stock_service.controller;

import com.example.stock_service.entity.Stock;
import com.example.stock_service.service.StockService;
import com.example.stock_service.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    // REST API TO CREATE STOCK
    @PostMapping
    public ResponseEntity<ApiResponse<Stock>> createStock(@Valid @RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        ApiResponse<Stock> response = new ApiResponse<>(
                "SUCCESS",
                "Stock created successfully",
                createdStock,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // REST API TO FIND STOCK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Stock>> findStockById(@PathVariable int id) {
        Stock stockById = stockService.findStockById(id);
        ApiResponse<Stock> response = new ApiResponse<>(
                "SUCCESS",
                "Stock fetched successfully",
                stockById,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO GET ALL STOCKS
    @GetMapping
    public ResponseEntity<ApiResponse<List<Stock>>> findAllStock() {
        List<Stock> stocks = stockService.findAllStock();
        ApiResponse<List<Stock>> response = new ApiResponse<>(
                "SUCCESS",
                "All stocks fetched successfully",
                stocks,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO UPDATE THE PRICE OF A STOCK
    @PutMapping("/updatePrice")
    public ResponseEntity<ApiResponse<Stock>> updateStock(@RequestParam int id, @RequestParam double price) {
        Stock updatedStock = stockService.updatePrice(id, price);
        ApiResponse<Stock> response = new ApiResponse<>(
                "SUCCESS",
                "Stock price updated successfully",
                updatedStock,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO DELETE A STOCK BASED ON ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStock(@PathVariable int id) {
        stockService.deleteStock(id);
        ApiResponse<String> response = new ApiResponse<>(
                "SUCCESS",
                "Stock deleted successfully",
                "Deleted Successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO ADD NEW STOCKS
    @PostMapping("/addNewStock")
    public ResponseEntity<ApiResponse<List<Stock>>> addNewStock(@Valid @RequestBody List<Stock> stocks) {
        List<Stock> updatedStocks = stockService.addNewStock(stocks);
        ApiResponse<List<Stock>> response = new ApiResponse<>(
                "SUCCESS",
                "New stocks added successfully",
                updatedStocks,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // REST API TO GET STOCKS BY CATEGORY
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Stock>>> findStockByCategory(@PathVariable String category) {
        List<Stock> stocks = stockService.findStockByCategory(category);
        ApiResponse<List<Stock>> response = new ApiResponse<>(
                "SUCCESS",
                "Stocks fetched by category successfully",
                stocks,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO GET STOCK QUANTITY BY ID
    @GetMapping("/quantity/{id}")
    public ResponseEntity<ApiResponse<Integer>> getStockQuantity(@PathVariable int id) {
        int quantity = stockService.getStockQuantity(id);
        ApiResponse<Integer> response = new ApiResponse<>(
                "SUCCESS",
                "Stock quantity fetched successfully",
                quantity,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO BULK UPDATE STOCK PRICES
    @PutMapping("/bulkUpdatePrice")
    public ResponseEntity<ApiResponse<List<Stock>>> bulkUpdateStockPrice(@RequestBody List<Stock> stocks) {
        List<Stock> updatedStocks = stockService.bulkUpdateStockPrice(stocks);
        ApiResponse<List<Stock>> response = new ApiResponse<>(
                "SUCCESS",
                "Stock prices updated successfully",
                updatedStocks,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO GET STOCKS BY PRICE RANGE
    @GetMapping("/priceRange")
    public ResponseEntity<ApiResponse<List<Stock>>> findStockByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Stock> stocks = stockService.findStockByPriceRange(minPrice, maxPrice);
        ApiResponse<List<Stock>> response = new ApiResponse<>(
                "SUCCESS",
                "Stocks fetched by price range successfully",
                stocks,
                null
        );
        return ResponseEntity.ok(response);
    }

    // REST API TO GET STOCK BY PRODUCT NAME
    @GetMapping("/productName/{productName}")
    public ResponseEntity<ApiResponse<Stock>> findStockByProductName(@PathVariable String productName) {
        Stock stock = stockService.findStockByProductName(productName);
        ApiResponse<Stock> response = stock != null
                ? new ApiResponse<>("SUCCESS", "Stock fetched successfully", stock, null)
                : new ApiResponse<>("ERROR", "Stock not found with the given product name", null, null);
        return ResponseEntity.status(stock != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    // REST API TO HANDLE QUANTITY AFTER SELL
    @PutMapping("/updateQuantityAfterSell")
    public ResponseEntity<ApiResponse<Stock>> updateQuantityAfterSell(@RequestParam int sellQuantity, @RequestParam int stockId) {
        Stock updatedStock = stockService.updateQuantityAfterSell(sellQuantity, stockId);
        ApiResponse<Stock> response = new ApiResponse<>(
                "SUCCESS",
                "Stock quantity updated successfully after sell",
                updatedStock,
                null
        );
        return ResponseEntity.ok(response);
    }
}
