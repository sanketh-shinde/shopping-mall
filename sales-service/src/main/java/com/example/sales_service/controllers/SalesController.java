package com.example.sales_service.controllers;

import com.example.sales_service.entities.Sales;
import com.example.sales_service.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales/api")
public class SalesController {


    @Autowired
    private SalesService service;

    @PostMapping("/addSales")
    public void addSales(@RequestBody Sales sales)
    {
    service.buySales(sales);
    }

}
