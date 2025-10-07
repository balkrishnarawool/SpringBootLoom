package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private RestClient restClient;

    public CustomerService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Customer getCustomer(String customerId) {
        var requestId = RequestContext.getRequestId();

        logger.info("{} CustomerService.getCustomer(): Start", requestId);

        var customer = restClient.get().uri("/customer/{id}", customerId).retrieve().body(Customer.class);

        logger.info("{} CustomerService.getCustomer(): Done", requestId);
        return customer;
    }
}
