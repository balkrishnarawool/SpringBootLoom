package com.balarawool.bootloom.abank.services.domain;

public interface Model {

    record Customer(String id) { }
    record Account(String number, String balance) { }
    record Loan(String number, String amount) { }
    record LoanApplicationRequest(String amount, String purpose) { }
    record Offer(String offerText) { }
    record CreditScore(String score) { }
}
