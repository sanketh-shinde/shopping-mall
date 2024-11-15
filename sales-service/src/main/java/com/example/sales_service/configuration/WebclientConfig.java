package com.example.sales_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebclientConfig {

    @Bean
    public WebClient.Builder webclient()
    {
        return  WebClient.builder();
    }

}
