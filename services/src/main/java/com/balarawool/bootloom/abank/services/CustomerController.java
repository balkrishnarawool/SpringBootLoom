package com.balarawool.bootloom.abank.services;

import com.balarawool.bootloom.abank.services.domain.Model.Account;
import com.balarawool.bootloom.abank.services.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.services.domain.Model.Customer;
import com.balarawool.bootloom.abank.services.domain.Model.Loan;
import com.balarawool.bootloom.abank.services.domain.Model.LoanOfferRequest;
import com.balarawool.bootloom.abank.services.domain.Model.Offer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.balarawool.bootloom.abank.services.util.ServicesUtil.logAndWait;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") String customerId) {
        logAndWait("getCustomer");
        return new Customer(customerId);
    }

    @GetMapping("/{id}/credit-score1")
    public CreditScore getCreditScore1(@PathVariable("id") String customerId) {
        logAndWait("getCreditScore1");
        return new CreditScore("Score1");
    }

    @GetMapping("/{id}/credit-score2")
    public CreditScore getCreditScore2(@PathVariable("id") String customerId) {
        logAndWait("getCreditScore2");
        return new CreditScore("Score2");
    }

    @GetMapping("/{id}/accounts")
    public List<Account> getAccountsInfo(@PathVariable("id") String customerId) {
        logAndWait("getAccountsInfo");
        return List.of(new Account("123", "1000.00"), new Account("456", "2000.00"));
    }

    @GetMapping("/{id}/loans")
    public List<Loan> getLoansInfo(@PathVariable("id") String customerId) {
        logAndWait("getLoansInfo");
        return List.of(new Loan("TL123", "10000.00"), new Loan("CL456", "20000.00"));
    }

    @PostMapping("/{id}/loans/offer")
    public Offer calculateOffer(@PathVariable("id") String customerId, @RequestBody LoanOfferRequest request) {
        logAndWait("calculateOffer");
        return new Offer("LMN123", request.amount(), request.purpose(), "4.00","An offer for your loan application");
    }
}
