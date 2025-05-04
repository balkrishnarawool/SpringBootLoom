package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AccountService {
    private RestClient restClient;

    public AccountService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Model.Account> getAccountsInfo(Customer customer) {
        return restClient
                .get()
                .uri("/customer/{id}/accounts", customer.id())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
