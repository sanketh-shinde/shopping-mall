package com.example.sales_service.services;

import com.example.sales_service.dto.DetailsDTO;
import com.example.sales_service.dto.IncentiveDto;
import com.example.sales_service.dto.Role;
import com.example.sales_service.entities.Incentives;
import com.example.sales_service.entities.Sales;
import com.example.sales_service.repository.IncentivesRepo;
import com.example.sales_service.repository.SalesRepo;
import com.example.sales_service.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.sales_service.helper.Constants.*;

@Service
public class IncentiveService {


    @Autowired
    private IncentivesRepo incentivesRepo;

    @Autowired
    private WebClient.Builder builder;

    @Autowired
    private SalesService salesService;

    @Autowired
    private SalesRepo salesRepo;

    public List<Incentives> addIncentives(IncentiveDto incentives)
    {
        Incentives incentives1=new Incentives();
        ApiResponse<ArrayList<Integer>> apiResponse = builder.build()
                .get()
                .uri("http://192.168.2.78:8081/employees/hierarchy/{employeeId}", incentives.getEmployeeId())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ArrayList<Integer>>>() {
                }).block();


        List<Incentives>list1=new ArrayList<>();

        if(apiResponse!=null)
       {
           ArrayList<Integer> list = apiResponse.getData();
           int k=1;
           for(Integer i:list)
           {
               ApiResponse<DetailsDTO> detailsDTOApiResponse =salesService.getEmployeeApiResponse(i);
               DetailsDTO data = detailsDTOApiResponse.getData();
               List<Role> roles = data.getRoles();
             Sales sales =salesRepo.findByEmployeeId(incentives.getEmployeeId());
               double salesAmount=sales.getSalesAmount();

               if (roles.size() == 1) {
                   Incentives incentives2=new Incentives();
                   incentives2.setEmployeeId(data.getId());
                   incentives2.setIncentiveAmount(salesAmount * EMPLOYEE_INCENTIVE);
                   incentives2.setTotalAmount(data.getSalary()+incentives1.getIncentiveAmount());
                   incentives2.setSales(sales);
                   LocalDateTime salesDate = sales.getSalesDate();
                   incentives2.setIncentiveMonthYear(salesDate.getMonth()+"-" +salesDate.getYear());
                   list1.add(incentivesRepo.save(incentives2));
               }
               else if (roles.stream().anyMatch(role -> role.getDesignation().equals("ROLE_FLOOR_SUPERVISOR"))) {
                   Incentives incentives3=new Incentives();
                   incentives3.setEmployeeId(data.getId());
                   incentives3.setIncentiveAmount(salesAmount*FLOOR_SUPERVISOR_INCENTIVE );
                   incentives3.setTotalAmount(data.getSalary()+incentives1.getIncentiveAmount());
                   incentives3.setSales(sales);
                   LocalDateTime salesDate = sales.getSalesDate();
                   incentives3.setIncentiveMonthYear(salesDate.getMonth()+"-" +salesDate.getYear());
                   list1.add(incentivesRepo.save(incentivesRepo.save(incentives3)));
               }


               else  {
                   Incentives incentives4 = new Incentives();
                   incentives4.setEmployeeId(data.getId());
                   incentives4.setIncentiveAmount(salesAmount*SALES_SUPERVISOR_INCENTIVE);
                   incentives4.setTotalAmount(data.getSalary() + incentives1.getIncentiveAmount());
                   incentives4.setSales(sales);
                   LocalDateTime salesDate = sales.getSalesDate();
                   incentives4.setIncentiveMonthYear(salesDate.getMonth() + "-" + salesDate.getYear());
                   list1.add(incentivesRepo.save(incentives4));
               }
           }

       }

        return list1;
    }
}
