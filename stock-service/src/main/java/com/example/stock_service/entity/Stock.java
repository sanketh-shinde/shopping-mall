package com.example.stock_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @NotBlank(message = "Category Name is Mandatory")
    private String category;
    @NotBlank(message = "Product Name is Mandatory")
    private String productName ;
    @NotBlank(message = " no of Quantity  is Mandatory")
    private int quantity;
    @NotBlank(message = "Price of Product  is Mandatory")
    private double price;
}
