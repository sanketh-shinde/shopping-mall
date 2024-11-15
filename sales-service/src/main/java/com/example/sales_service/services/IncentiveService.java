package com.example.sales_service.services;

import com.example.sales_service.dto.Employee;
import com.example.sales_service.entities.Incentives;
import com.example.sales_service.entities.Sales;
import com.example.sales_service.helper.Constants;
import com.example.sales_service.repository.IncentivesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Month;

@Service
public class IncentiveService {


    @Autowired
    private IncentivesRepo incentivesRepo;

    @Autowired
    private WebClient.Builder builder;

    public Incentives addIncentives(Incentives incentives)
    {

        Employee employee= builder.build()
                .get()
                .uri("http://192.168.2.98:8081/employees/get/{employeeId}",incentives.getEmployeeId(),Employee.class)
                .retrieve()
                .bodyToMono(Employee.class).block();
        String designation=employee.getManager().getRoleMapping().getRole().getDesignation();
        double salesAmount=incentives.getSales().getSalesAmount();
        double incentiveAmount=incentives.getIncentiveAmount();

        if(designation.equals("Employee"))
        {
           incentiveAmount=Constants.EMPLOYEE_INCENTIVE*salesAmount;
           incentives.setIncentiveAmount(incentiveAmount);
        }
        if(designation.equals("Floor Supervisor"))
        {
            incentiveAmount=Constants.FLOOR_SUPERVISOR_INCENTIVE*salesAmount;
            incentives.setIncentiveAmount(incentiveAmount);
        }
        if(designation.equalsIgnoreCase("Sales Supervisor"))
        {
            incentiveAmount=Constants.SALES_SUPERVISOR_INCENTIVE*salesAmount;
            incentives.setIncentiveAmount(incentiveAmount);
        }
        if(designation.equalsIgnoreCase("Accountant"))
        {
            incentiveAmount=Constants.ACCOUNTANT*salesAmount;
            incentives.setIncentiveAmount(incentiveAmount);
        }
        else
        {
            incentiveAmount=0;
            incentives.setIncentiveAmount(incentiveAmount);
        }
        incentives.setTotalAmount(employee.getSalary()+incentiveAmount);

        LocalDate localDate =incentives.getSales().getSalesDate();
        Month month=localDate.getMonth();
        int year =localDate.getYear();
        incentives.setIncentiveMonthYear(month+"/"+year);
        return incentivesRepo.save(incentives);
    }
}
