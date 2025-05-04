package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private RestClient restClient;

    public AccountService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Model.Account> getAccountsInfo(Customer customer) {
        logger.info("AccountService.getAccountsInfo(): Start");

        var accounts = restClient
                .get()
                .uri("/customer/{id}/accounts", customer.id())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Model.Account>>() {
                });
        logger.info("AccountService.getAccountsInfo(): Done");
        return accounts;
    }
}
