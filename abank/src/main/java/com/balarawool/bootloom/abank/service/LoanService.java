package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.LoanOfferRequest;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import org.springframework.core.ParameterizedTypeReference;
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
        return CompletableFuture.supplyAsync(() -> restClient
                        .get()
                        .uri("/customer/{id}/loans", customer.id())
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() { })
        );
    }

    public CompletableFuture<Offer> calculateOffer(String customerId,
                                                   List<Account> accountsInfo,
                                                   List<Loan> loansInfo,
                                                   CreditScore creditScore,
                                                   String amount,
                                                   String purpose) {
        var loanOfferRequest = new LoanOfferRequest(accountsInfo, loansInfo, creditScore.score(), amount, purpose);
        return CompletableFuture.supplyAsync(() -> restClient
                        .post()
                        .uri("/customer/{id}/loans/offer", customerId)
                        .body(loanOfferRequest)
                        .retrieve()
                        .body(Offer.class)
        );
    }
}
