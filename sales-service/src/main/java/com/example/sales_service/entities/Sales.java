package com.example.sales_service.entities;

import com.example.sales_service.dto.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesId;
    private double salesAmount;


    @Column(nullable = false)
    private LocalDateTime salesDate=LocalDateTime.now();

    private Integer employeeId;
    @Lob
    private String stocks;





}
