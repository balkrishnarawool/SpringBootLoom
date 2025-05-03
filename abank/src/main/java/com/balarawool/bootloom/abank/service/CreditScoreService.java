package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model;
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

    public Model.CreditScore getCreditScore(Customer customer) {
        try (var scope = StructuredTaskScope.open(Joiner.<Model.CreditScore>anySuccessfulResultOrThrow())) {
            scope.fork(() -> getCreditScoreFrom("/credit-score1", customer));
            scope.fork(() -> getCreditScoreFrom("/credit-score2", customer));

            var score = scope.join();
            return score;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Model.CreditScore getCreditScoreFrom(String endpoint, Customer customer) {
        return restClient.get().uri("/customer/{id}"+endpoint, customer.id()).retrieve().body(Model.CreditScore.class);
    }
}
