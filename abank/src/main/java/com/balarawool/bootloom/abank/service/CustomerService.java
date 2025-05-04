package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerService {
    private RestClient restClient;

    public CustomerService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Customer getCustomer(String customerId) {
        return restClient.get().uri("/customer/{id}", customerId).retrieve().body(Customer.class);
    }
}
