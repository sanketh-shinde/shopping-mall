package com.example.sales_service.entities;


import com.example.sales_service.dto.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesId;

    private Integer stockId;
    @JsonIgnore
    private double salesAmount;
    private LocalDate salesDate;
    private Integer employeeId;

    private List<String> sales;

}
