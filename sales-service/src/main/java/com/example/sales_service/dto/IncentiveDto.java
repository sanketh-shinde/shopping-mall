package com.example.sales_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncentiveDto {

    private Integer employeeId;
    private SalesDto salesDto;

}
