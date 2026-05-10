package com.balarawool.bootloom.abank.controller;

import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.LoanApplicationRequest;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import com.balarawool.bootloom.abank.service.AccountService;
import com.balarawool.bootloom.abank.service.CreditScoreService;
import com.balarawool.bootloom.abank.service.CustomerService;
import com.balarawool.bootloom.abank.service.LoanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.StructuredTaskScope;

@RestController
public class LoanController {
    private CustomerService customerService;
    private AccountService accountService;
    private LoanService loanService;
    private CreditScoreService creditScoreService;

    public LoanController(CustomerService customerService, AccountService accountService, LoanService loanService, CreditScoreService creditScoreService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.loanService = loanService;
        this.creditScoreService = creditScoreService;
    }

    @PostMapping("/loan-application")
    public Offer applyForLoan(@RequestBody LoanApplicationRequest request) {
        final var currentCustomer = customerService.getCustomer(request.customerId());

        try (var scope = StructuredTaskScope.open()) {
            final var task1 = scope.fork(() -> accountService.getAccountsInfo(currentCustomer));
            final var task2 = scope.fork(() -> loanService.getLoansInfo(currentCustomer));
            final var task3 = scope.fork(() -> creditScoreService.getCreditScore(currentCustomer));

            scope.join();

            final var accounts = task1.get();
            final var loans = task2.get();
            final var creditScore = task3.get();

            final var offer = loanService.calculateOffer(currentCustomer, accounts, loans, creditScore, request.amount(), request.purpose());
            return offer;
        } catch (InterruptedException e) {
            throw new ABankException(e);
        }

    }
}
