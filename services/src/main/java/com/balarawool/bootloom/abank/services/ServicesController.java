package com.balarawool.bootloom.abank.services;

import com.balarawool.bootloom.abank.services.domain.Model.Account;
import com.balarawool.bootloom.abank.services.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.services.domain.Model.Customer;
import com.balarawool.bootloom.abank.services.domain.Model.Loan;
import com.balarawool.bootloom.abank.services.domain.Model.Offer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.balarawool.bootloom.abank.services.util.ThreadUtil.logAndWait;

@RestController
public class ServicesController {
    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable("id") String customerId) {
        logAndWait("currentCustomer");
        return new Customer(customerId);
    }

    @GetMapping("/customer/{id}/credit-score1")
    public CreditScore getCreditScore1(@PathVariable("id") String customerId) {
        logAndWait("getCreditScore1");
        return new CreditScore("Score1");
    }

    @GetMapping("/customer/{id}/credit-score2")
    public CreditScore getCreditScore2(@PathVariable("id") String customerId) {
        logAndWait("getCreditScore2");
        return new CreditScore("Score2");
    }

    @GetMapping("/customer/{id}/accounts")
    public List<Account> getAccountsInfo(@PathVariable("id") String customerId) {
        logAndWait("getAccountsInfo");
        return List.of(new Account("123", "1000.00"), new Account("456", "2000.00"));
    }

    @GetMapping("/customer/{id}/loans")
    public List<Loan> getLoansInfo(@PathVariable("id") String customerId) {
        logAndWait("getLoansInfo");
        return List.of(new Loan("TL123", "10000.00"), new Loan("CL456", "20000.00"));
    }

    @GetMapping("/customer/{id}/loans/offer")
    public Offer calculateOffer(@PathVariable("id") String customerId) {
        logAndWait("calculateOffer");
        return new Offer("An offer for your loan application");
    }
}
