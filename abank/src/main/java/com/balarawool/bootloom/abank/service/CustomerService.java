package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {
    private RestClient restClient;

    public CustomerService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CompletableFuture<Customer> getCustomer(String customerId) {
        return CompletableFuture.supplyAsync(() ->
                restClient.get().uri("/customer/{id}", customerId).retrieve().body(Customer.class)
        );
    }
}
