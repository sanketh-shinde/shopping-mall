package com.example.sales_service.services;

import com.example.sales_service.dto.DetailsDTO;
import com.example.sales_service.dto.SalesDto;
import com.example.sales_service.dto.Stock;
import com.example.sales_service.entities.Sales;
import com.example.sales_service.exception.InsufficientStockException;
import com.example.sales_service.exception.StockNotFoundException;
import com.example.sales_service.repository.SalesRepo;
import com.example.sales_service.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private WebClient.Builder webclient;

    @Autowired
    private HttpServletRequest request;


    //For buying the products
    public Sales buySales(SalesDto salesDto) throws IOException {

        ApiResponse<DetailsDTO>apiResponse=getEmployeeApiResponse(salesDto.getEmployeeId());
        DetailsDTO detailsDTO =  apiResponse.getData();
        salesDto.setEmployeeId(detailsDTO.getId());

        List<Stock>stockList =salesDto.getStocks();
        List<Stock>list=new ArrayList<>();

        for(Stock stock : stockList)
        {
            ApiResponse<Stock> stockApiResponse=stockApiResponse(stock.getId());
            if(stockApiResponse==null)
            {
                throw new StockNotFoundException("Stock with id " + stock.getId() + " is not found");
            }
            Stock stock1=stockApiResponse.getData();
            String s=updateStockQuantity(stock.getId(),stock.getQuantity());
            System.out.println(s);
            if(s.contains("BAD_REQUEST"))
            {
                throw new InsufficientStockException("Insufficient stock Exception \nRequested quantity: "
                        +stock1.getQuantity() +"\navailable quantity: "+stock.getQuantity());
            }
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
                .uri("http://192.168.2.86:8082/api/stocks/{id}", stockId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Stock>>() {})
                .block();
    }


    //To update the quantity column in the Stock entity
    public String updateStockQuantity(int sellQuantity, int stockId)
    {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://192.168.2.86:8082")
                .build();

        String block = webClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/stocks/updateQuantityAfterSell")  // Only the path now
                        .queryParam("sellQuantity", sellQuantity)
                        .queryParam("stockId", stockId)
                        .build())
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError()) {
                        clientResponse.body((clientHttpResponse, context) ->
                        {
                            return clientHttpResponse.getBody();
                        });
                        return clientResponse.bodyToMono(String.class);
                    }
                    return clientResponse.bodyToMono(String.class);
                })
                .block();
        return block;
    }


    //Returns employee based on employeeId
    public ApiResponse<DetailsDTO> getEmployeeApiResponse(int employeeId)
    {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

    return webclient.build().get()
                .uri("http://192.168.2.78:8081/employees/get/{employeeId}", employeeId)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError()) {
                        clientResponse.body((clientHttpResponse, context) ->
                                clientHttpResponse.getBody());
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<ApiResponse<DetailsDTO>>() {
                        });
                    } else {
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<ApiResponse<DetailsDTO>>() {
                        });
                    }
                }).block();
    }

}