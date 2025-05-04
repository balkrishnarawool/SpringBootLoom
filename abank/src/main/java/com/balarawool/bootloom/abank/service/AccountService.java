package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService {
    private RestClient restClient;

    public AccountService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CompletableFuture<List<Account>> getAccountsInfo(Customer customer) {
        return CompletableFuture.supplyAsync(() -> restClient
                        .get()
                        .uri("/customer/{id}/accounts", customer.id())
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<Account>>() { })
        );
    }
}
