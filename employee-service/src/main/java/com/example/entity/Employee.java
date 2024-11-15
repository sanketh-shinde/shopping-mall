package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private Integer id;

    private String name;
    private String phoneNumber;

    private LocalDate joiningDate;

}
