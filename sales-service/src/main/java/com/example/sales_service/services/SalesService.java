package com.example.sales_service.services;

import com.example.sales_service.dto.Employee;
import com.example.sales_service.dto.Stock;
import com.example.sales_service.entities.Sales;
import com.example.sales_service.exception.StockNotFoundException;
import com.example.sales_service.repository.SalesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private WebClient.Builder webclient;

    public void buySales(Sales sales)
    {
         Employee employee= webclient.build()
                .get()
                .uri("http://192.168.2.98:8081/employees/get/{employeeId}",sales.getEmployeeId(),Employee.class)
                .retrieve()
                .bodyToMono(Employee.class).block();

        Stock stock =webclient.build()
                .get()
                .uri("http://192.168.2.99:8082/api/stocks/{id}",sales.getStockId(),Stock.class)
                .retrieve()
                .bodyToMono(Stock.class).block();
        if(stock==null)
        {
            throw new StockNotFoundException("Stock with id "+sales.getStockId() +" is not found");
        }
        double stockPrice=stock.getPrice();
        int quantity=stock.getQuantity();
        double totalPrice=stockPrice*quantity;
        sales.setSalesAmount(totalPrice);
        for(String str:sales.getSales())
        {
            System.out.println(str);
        }
//        return salesRepo.save(sales);

    }
}
