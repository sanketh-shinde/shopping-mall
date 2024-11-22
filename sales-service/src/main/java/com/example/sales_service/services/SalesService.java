package com.example.sales_service.services;

import com.example.sales_service.dto.DetailsDTO;
import com.example.sales_service.dto.EmployeeDTO;
import com.example.sales_service.dto.SalesDto;
import com.example.sales_service.dto.Stock;
import com.example.sales_service.entities.Sales;
import com.example.sales_service.exception.EmployeeNotFoundException;
import com.example.sales_service.exception.StockNotFoundException;
import com.example.sales_service.repository.SalesRepo;
import com.example.sales_service.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private WebClient.Builder webclient;



    //For buying the products
    public Sales buySales(SalesDto salesDto) throws IOException {


                    ApiResponse<DetailsDTO>apiResponse=getEmployeeApiResponse(salesDto.getEmployeeId());
                    DetailsDTO detailsDTO =  apiResponse.getData();
                    salesDto.setEmployeeId(detailsDTO.getId());



                    List<Stock>stockList =salesDto.getStocks();
                    List<Stock>list=new ArrayList<>();

                    for(Stock stock:stockList)
                    {
                        ApiResponse<Stock> stockApiResponse=stockApiResponse(stock.getId());
                        if(stockApiResponse==null)
                        {
                            throw new StockNotFoundException("Stock with id "+stock.getId()+" is not found");
                        }
                        Stock stock1=stockApiResponse.getData();
                        stock1.setQuantity(stock.getQuantity());

                        updateStockQuantity(stock1.getQuantity(),stock.getId());


                        list.add(stock1);
                    }
                    salesDto.setStocks(list);
                    double totalAmount=0;
                    for(Stock stock:salesDto.getStocks())
                    {
                        totalAmount=totalAmount+(stock.getPrice()*stock.getQuantity());
                    }
                        salesDto.setSalesAmount(totalAmount);

                        Sales sales=new Sales();
                        sales.setSalesAmount(salesDto.getSalesAmount());
                        sales.setStocks(salesDto.getStocks().toString());
                        sales.setEmployeeId(salesDto.getEmployeeId());


                        return salesRepo.save(sales);
    }

    //Returns Stock object based on stockId
    public ApiResponse<Stock> stockApiResponse(int stockId)
    {
         return webclient.build()
                .get()
                .uri("http://192.168.2.86:8082/api/stocks/{id}",
                        stockId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Stock>>() {})
                .block();
    }


    //To update the quantity column in the Stock entity
    public void updateStockQuantity(int sellQuantity,int stockId)
    {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://192.168.2.86:8082")
                .build();

          webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/stocks/updateQuantityAfterSell")  // Only the path now
                        .queryParam("sellQuantity", sellQuantity)
                        .queryParam("stockId", stockId)
                        .build())
                .retrieve()
                .bodyToMono(Stock.class)
                .block();
    }


    //Returns employee based on employeeId
    public ApiResponse<DetailsDTO> getEmployeeApiResponse(int employeeId)
    {

        return webclient.build()
                .get()
                .uri("http://192.168.2.78:8081/employees/get/{employeeId}",employeeId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<DetailsDTO>>() {})
                .block();
    }

}