package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Service
public class CreditScoreService {
    private RestClient restClient;

    public CreditScoreService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CompletableFuture<?> getCreditScore(Customer customer) {
        var creditScore1CF = getCreditScoreFrom("/credit-score1", customer);
        var creditScore2CF = getCreditScoreFrom("/credit-score2", customer);

        return CompletableFuture.anyOf(creditScore1CF, creditScore2CF)
                .exceptionally(th -> {
                    throw new ABankException(th);
                });
    }

    private CompletableFuture<CreditScore> getCreditScoreFrom(String endpoint, Customer customer) {
        return CompletableFuture.supplyAsync(() ->
                restClient.get().uri("/customer/{id}"+endpoint, customer.id()).retrieve().body(CreditScore.class)
        );
    }
}
