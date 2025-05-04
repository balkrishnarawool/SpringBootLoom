package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.LoanOfferRequest;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.balarawool.bootloom.abank.domain.RequestMetadata.CURRENT_CUSTOMER;

@Service
public class LoanService {
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private RestClient restClient;

    public LoanService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Loan> getLoansInfo() {
        logger.info("LoanService.getLoansInfo(): Start");

        var customer = CURRENT_CUSTOMER.orElseThrow(() -> new ABankException("No customer available"));
        var loans = restClient
                .get()
                .uri("/customer/{id}/loans", customer.id())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Loan>>() { });

        logger.info("LoanService.getLoansInfo(): Done");
        return loans;
    }

    public Offer calculateOffer(List<Account> accountsInfo,
                                List<Loan> loansInfo,
                                CreditScore creditScore,
                                String amount,
                                String purpose) {
        logger.info("LoanService.calculateOffer(): Start");

        var customer = CURRENT_CUSTOMER.orElseThrow(() -> new ABankException("No customer available"));
        var loanOfferRequest = new LoanOfferRequest(accountsInfo, loansInfo, creditScore.score(), amount, purpose);
        var offer = restClient
                .post()
                .uri("/customer/{id}/loans/offer", customer.id())
                .body(loanOfferRequest)
                .retrieve()
                .body(Offer.class);

        logger.info("LoanService.calculateOffer(): Done");
        return  offer;
    }
}
