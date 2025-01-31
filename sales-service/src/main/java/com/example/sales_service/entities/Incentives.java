package com.example.sales_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Incentives {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer incentiveId;


        private Integer employeeId;

        @JsonIgnore
        private String incentiveMonthYear;

        @ManyToOne
        private Sales sales;
        @JsonIgnore
        private double incentiveAmount;
        @JsonIgnore
        private double totalAmount;

}
