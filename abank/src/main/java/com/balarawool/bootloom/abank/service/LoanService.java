package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.LoanOfferRequest;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import com.balarawool.bootloom.abank.domain.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class LoanService {
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private RestClient restClient;

    public LoanService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Loan> getLoansInfo(Customer customer) {
        var requestId = RequestContext.getRequestId();

        logger.info("{} LoanService.getLoansInfo(): Start", requestId);

        var loans = restClient
                .get()
                .uri("/customer/{id}/loans", customer.id())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Loan>>() { });

        logger.info("{} LoanService.getLoansInfo(): Done", requestId);
        return loans;
    }

    public Offer calculateOffer(Customer customer,
                                List<Account> accountsInfo,
                                List<Loan> loansInfo,
                                CreditScore creditScore,
                                String amount,
                                String purpose) {
        var requestId = RequestContext.getRequestId();

        logger.info("{} LoanService.calculateOffer(): Start", requestId);

        var loanOfferRequest = new LoanOfferRequest(accountsInfo, loansInfo, creditScore.score(), amount, purpose);
        var offer = restClient
                .post()
                .uri("/customer/{id}/loans/offer", customer.id())
                .body(loanOfferRequest)
                .retrieve()
                .body(Offer.class);

        logger.info("{} LoanService.calculateOffer(): Done", requestId);
        return  offer;
    }
}
