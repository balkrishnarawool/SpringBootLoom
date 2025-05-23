package com.balarawool.bootloom.abank.services.domain;

import java.util.List;

public interface Model {

    record Customer(String id) { }
    record Account(String number, String balance) { }
    record Loan(String number, String amount) { }
    record CreditScore(String score) { }
    record LoanOfferRequest(List<Account> accounts, List<Loan> loans, String creditScore, String amount, String purpose) { }
    record Offer(String id, String amount, String purpose, String interest, String offerText) { }
}
