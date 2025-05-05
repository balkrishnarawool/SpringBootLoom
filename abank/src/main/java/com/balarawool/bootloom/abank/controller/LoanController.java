package com.balarawool.bootloom.abank.controller;

import com.balarawool.bootloom.abank.domain.Model.ABankException;
import com.balarawool.bootloom.abank.domain.Model.Account;
import com.balarawool.bootloom.abank.domain.Model.CreditScore;
import com.balarawool.bootloom.abank.domain.Model.Loan;
import com.balarawool.bootloom.abank.domain.Model.LoanApplicationRequest;
import com.balarawool.bootloom.abank.domain.Model.Offer;
import com.balarawool.bootloom.abank.service.AccountService;
import com.balarawool.bootloom.abank.service.CreditScoreService;
import com.balarawool.bootloom.abank.service.CustomerService;
import com.balarawool.bootloom.abank.service.LoanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

import static com.balarawool.bootloom.abank.domain.RequestMetadata.CURRENT_CUSTOMER;

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
    public Offer applyForLoan(@RequestBody LoanApplicationRequest request) {
        var currentCustomer = customerService.getCustomer(request.customerId());
        return ScopedValue.where(CURRENT_CUSTOMER, currentCustomer)
                .call(() -> {
                    var customerInfo = getCustomerInfo();
                    var offer = loanService.calculateOffer(
                            customerInfo.accounts(), customerInfo.loans(), customerInfo.creditScore(), request.amount(), request.purpose()
                    );
                    return offer;
                });
    }

    private record CustomerInfo(List<Account> accounts, List<Loan> loans, CreditScore creditScore) { }
    private CustomerInfo getCustomerInfo() {
        try (var scope = StructuredTaskScope.open()) {
            var task1 = scope.fork(accountService::getAccountsInfo);
            var task2 = scope.fork(loanService::getLoansInfo);
            var task3 = scope.fork(creditScoreService::getCreditScore);

            scope.join();
            var accountsInfo = task1.get();
            var loansInfo = task2.get();
            var creditScore = task3.get();

            return new CustomerInfo(accountsInfo, loansInfo, creditScore);
        } catch (InterruptedException e) {
            throw new ABankException(e);
        }
    }
}
