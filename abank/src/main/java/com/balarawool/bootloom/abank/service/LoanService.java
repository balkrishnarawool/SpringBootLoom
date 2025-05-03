package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class LoanService {
    private RestClient restClient;

    public LoanService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Loan> getLoansInfo(Customer customer) {
        return restClient.get().uri("/customer/{id}/loans", customer.id()).retrieve().body(List.class);
    }

    public Offer calculateOffer(Customer customer, CreditScore creditScore, List<Account> accountsInfo, List<Loan> loansInfo) {
        return restClient.get().uri("/customer/{id}/loans/offer", customer.id()).retrieve().body(Offer.class);
    }
}
