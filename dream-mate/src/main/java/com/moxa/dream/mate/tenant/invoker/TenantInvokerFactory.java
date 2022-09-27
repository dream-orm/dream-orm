package com.moxa.dream.mate.tenant.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class TenantInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "dream-mate-tenant";
    public static final String TENANT = "dream-mate-tenant";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case TENANT:
                return new TenantInvoker();
        }
        return null;
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
