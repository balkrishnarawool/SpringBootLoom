package com.balarawool.bootloom.abank.domain;

import com.balarawool.bootloom.abank.domain.Model.Customer;

import java.lang.ScopedValue.CallableOp;
import java.util.UUID;

public class RequestContext {

    private static final ScopedValue<UUID> REQUEST_ID = ScopedValue.newInstance();

    public static Request withRequestId(UUID requestId) {
       return new Request(ScopedValue.where(REQUEST_ID, requestId));
    }
    
    public static UUID getRequestId() {
        return REQUEST_ID.orElseThrow(() -> new Model.ABankException("No request ID available"));
    }

    public static class Request {
        private ScopedValue.Carrier carrier;

        private Request(ScopedValue.Carrier carrier) {
            this.carrier = carrier;
        }

        public <T, X extends Throwable> T call(CallableOp<T, X> callableOp) throws X {
            return carrier.call(callableOp);
        }
    }
}
