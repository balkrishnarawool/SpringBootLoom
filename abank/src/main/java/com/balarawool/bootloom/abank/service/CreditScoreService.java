package com.balarawool.bootloom.abank.service;

import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CreditScoreService {
    private static final Logger logger = LoggerFactory.getLogger(CreditScoreService.class);

    private RestClient restClient;

    public CreditScoreService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CreditScore getCreditScore(Customer customer) {
        return null;
    }

    private CreditScore getCreditScoreFrom(String endpoint, Customer customer) {
        logger.info("CreditScoreService.getCreditScore() with {}: Start", endpoint);

        var score = restClient.get().uri("/customer/{id}/{endpoint}", customer.id(), endpoint).retrieve().body(CreditScore.class);

        logger.info("CreditScoreService.getCreditScore() with {}: Done", endpoint);
        return score;
    }
}
