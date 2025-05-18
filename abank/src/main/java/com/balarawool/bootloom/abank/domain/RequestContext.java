package com.balarawool.bootloom.abank.domain;

import com.balarawool.bootloom.abank.domain.Model.Customer;

import java.lang.ScopedValue.CallableOp;

public class RequestContext {

    private static final ScopedValue<Customer> CURRENT_CUSTOMER = ScopedValue.newInstance();

    public static Request withCustomer(Customer customer) {
       return new Request(ScopedValue.where(CURRENT_CUSTOMER, customer));
    }
    
    public static Customer getCurrentCustomer() {
        return CURRENT_CUSTOMER.orElseThrow(() -> new Model.ABankException("No customer available"));
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
