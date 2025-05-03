package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LoanService {
    private RestClient restClient;

    public LoanService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CompletableFuture<List<Loan>> getLoansInfo(Customer customer) {
        return CompletableFuture.supplyAsync(() ->
                (List<Loan>) restClient.get().uri("/customer/{id}/loans", customer.id()).retrieve().body(List.class)
        );
    }

    public CompletableFuture<Offer> calculateOffer(Customer customer, CreditScore creditScore, List<Account> accountsInfo, List<Loan> loansInfo) {
        return CompletableFuture.supplyAsync(() ->
            restClient.get().uri("/customer/{id}/loans/offer", customer.id()).retrieve().body(Offer.class)
        );
    }
}
