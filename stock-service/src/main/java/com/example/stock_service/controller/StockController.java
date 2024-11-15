package com.example.stock_service.controller;

import com.example.stock_service.entity.Stock;
import com.example.stock_service.service.StockService;
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
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    // REST API TO FIND STOCK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> findStockById(@PathVariable int id) {
        Stock stockById = stockService.findStockById(id);
        return new ResponseEntity<>(stockById, HttpStatus.OK);
}

    // REST API TO GET ALL STOCKS
    @GetMapping
    public ResponseEntity<List<Stock>> findAllStock() {
        List<Stock> stocks = stockService.findAllStock();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    // REST API TO UPDATE THE PRICE OF A STOCK
    @PutMapping("/updatePrice")
    public ResponseEntity<Stock> updateStock(@RequestParam int id, @RequestParam double price) {
        Stock updatedStock = stockService.updatePrice(id, price);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    // REST API TO DELETE A STOCK BASED ON ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable int id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    // REST API TO ADD NEW STOCKS
    @PostMapping("/addNewStock")
    public ResponseEntity<List<Stock>> addNewStock(@Valid @RequestBody List<Stock> stocks) {
        List<Stock> updatedStocks = stockService.addNewStock(stocks);
        return new ResponseEntity<>(updatedStocks, HttpStatus.CREATED);
    }
}
