package com.balarawool.bootloom.abank.domain;

import com.balarawool.bootloom.abank.domain.Model.Customer;

public class RequestContext {

    public static final ScopedValue<Customer> CURRENT_CUSTOMER = ScopedValue.newInstance();
}
