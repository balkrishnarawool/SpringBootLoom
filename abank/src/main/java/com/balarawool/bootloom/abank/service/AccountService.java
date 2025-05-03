package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model;
import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.RequestMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.balarawool.bootloom.abank.domain.RequestMetadata.CURRENT_CUSTOMER;

@Service
public class AccountService {
    private RestClient restClient;

    public AccountService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Model.Account> getAccountsInfo() {
        var customer = CURRENT_CUSTOMER.orElseThrow(() -> new ABankException("No customer available"));
        return restClient.get().uri("/customer/{id}/accounts", customer.id()).retrieve().body(List.class);
    }
}
