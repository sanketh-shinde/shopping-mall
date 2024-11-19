package com.example.sales_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

            private Integer id;
            private String category;
            private String dressName;
            private int quantity;
            private double price;
}
