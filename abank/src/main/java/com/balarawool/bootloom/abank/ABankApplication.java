package com.balarawool.bootloom.abank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ABankApplication {

    @Value("${abank.services.base-url}")
    private String servicesBaseUrl;

    public static void main(String[] args) {
        SpringApplication.run(ABankApplication.class, args);
    }

    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.baseUrl(servicesBaseUrl).build();
    }
}
