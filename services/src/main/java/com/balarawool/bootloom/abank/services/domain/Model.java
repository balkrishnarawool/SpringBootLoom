package com.balarawool.bootloom.abank.services.domain;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> afdc184 (Added model for services app)
public interface Model {

    record Customer(String id) { }
    record Account(String number, String balance) { }
    record Loan(String number, String amount) { }
<<<<<<< HEAD
    record CreditScore(String score) { }
    record LoanOfferRequest(List<Account> accounts, List<Loan> loans, String creditScore, String amount, String purpose) { }
    record Offer(String id, String amount, String purpose, String interest, String offerText) { }
=======
    record LoanApplicationRequest(String amount, String purpose) { }
    record Offer(String offerText) { }
    record CreditScore(String score) { }
>>>>>>> afdc184 (Added model for services app)
}
