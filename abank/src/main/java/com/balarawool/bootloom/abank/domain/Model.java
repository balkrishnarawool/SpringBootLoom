package com.balarawool.bootloom.abank.domain;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface Model {

    record LoanApplicationRequest(String customerId, String amount, String purpose) { }
    record Customer(String id) { }
    record Account(String number, String balance) { }
    record Loan(String number, String amount) { }
    record CreditScore(String score) { }
    record LoanOfferRequest(List<Account> accounts, List<Loan> loans, String creditScore, String amount, String purpose) { }
    record Offer(String id, String amount, String purpose, String interest, String offerText) { }

    class ABankException extends RuntimeException {
        public ABankException(String message) {
            super(message);
        }

        public ABankException(Throwable th) {
            super(th);
        }
    }

    record Node(String id, URI healthUri) { }
    record Region(String name, List<Node> nodes) { }
    record NodeProbe(String nodeId, boolean ok, Duration latency, Optional<String> error) { }
    record RegionProbeResult(String region,
                                    int okCount,
                                    int totalCount,
                                    boolean active,
                                    List<NodeProbe> details) { }

}
