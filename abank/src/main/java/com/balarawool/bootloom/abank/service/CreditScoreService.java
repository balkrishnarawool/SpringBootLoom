package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

@Service
public class CreditScoreService {
    private RestClient restClient;

    public CreditScoreService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CreditScore getCreditScore(Customer customer) {
        try (var scope = StructuredTaskScope.open(Joiner.<CreditScore>anySuccessfulResultOrThrow())) {
            scope.fork(() -> getCreditScoreFrom("/credit-score1", customer));
            scope.fork(() -> getCreditScoreFrom("/credit-score2", customer));

            var score = scope.join();
            return score;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private CreditScore getCreditScoreFrom(String endpoint, Customer customer) {
        return restClient.get().uri("/customer/{id}"+endpoint, customer.id()).retrieve().body(CreditScore.class);
    }
}
