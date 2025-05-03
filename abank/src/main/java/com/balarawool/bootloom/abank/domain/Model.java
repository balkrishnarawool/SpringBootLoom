package com.balarawool.bootloom.abank.domain;

public interface Model {

    record Customer(String id) { }
    record Account(String number, String balance) { }
    record Loan(String number, String amount) { }
    record Offer(String offerText) { }
    record CreditScore(String score) { }

    class ABankException extends RuntimeException {
        public ABankException(String message) {
            super(message);
        }
    }
}
