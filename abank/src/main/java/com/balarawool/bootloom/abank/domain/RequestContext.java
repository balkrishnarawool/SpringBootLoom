package com.balarawool.bootloom.abank.domain;

import java.util.UUID;

public class RequestContext {

    public static final ScopedValue<UUID> REQUEST_ID = ScopedValue.newInstance();
}
