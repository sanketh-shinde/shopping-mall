package com.example.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesDto {

    private Integer salesId;
    private double salesAmount;
    private LocalDateTime salesDate;
    private Integer employeeId;
    private List<Stock> stocks;

}
