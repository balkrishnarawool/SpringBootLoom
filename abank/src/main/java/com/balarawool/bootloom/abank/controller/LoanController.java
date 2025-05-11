package com.balarawool.bootloom.abank.controller;

import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import com.balarawool.bootloom.abank.domain.Model.LoanApplicationRequest;
import com.balarawool.bootloom.abank.service.AccountService;
import com.balarawool.bootloom.abank.service.CreditScoreService;
import com.balarawool.bootloom.abank.service.CustomerService;
import com.balarawool.bootloom.abank.service.LoanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class LoanController {
    private CustomerService customerService;
    private AccountService accountService;
    private LoanService loanService;
    private CreditScoreService creditScoreService;

    public LoanController(CustomerService customerService,
                          AccountService accountService,
                          LoanService loanService,
                          CreditScoreService creditScoreService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.loanService = loanService;
        this.creditScoreService = creditScoreService;
    }

    @PostMapping("/loan-application")
    public CompletableFuture<Offer> applyForLoan(@RequestBody LoanApplicationRequest request)  {
        var currentCustomer = customerService.getCustomer(request.customerId())
                .exceptionally(th -> {
                    throw new ABankException(th);
                })
                .join();

        var accountsInfoCF = accountService.getAccountsInfo(currentCustomer);
        var loansInfoCF = loanService.getLoansInfo(currentCustomer);
        var creditScoreCF = creditScoreService.getCreditScore(currentCustomer);

        return CompletableFuture.allOf(creditScoreCF, accountsInfoCF, loansInfoCF)
                .exceptionally(th -> {
                    throw new ABankException(th);
                })
                .thenCompose(_ -> {
                    var creditScore = (CreditScore) creditScoreCF.join();
                    var accountsInfo = accountsInfoCF.join();
                    var loansInfo = loansInfoCF.join();

                    var offer = loanService.calculateOffer(
                            currentCustomer.id(), accountsInfo, loansInfo, creditScore, request.amount(), request.purpose()
                    ).exceptionally(th -> {
                        throw new ABankException(th);
                    });
                    return offer;
                });
    }
}
