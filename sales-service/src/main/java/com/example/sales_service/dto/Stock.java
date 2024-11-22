package com.example.sales_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {

    private int id;
    private String category;
    private String productName ;
    private int quantity;
    private double price;

}
