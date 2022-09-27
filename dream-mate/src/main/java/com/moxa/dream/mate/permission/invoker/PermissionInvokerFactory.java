package com.moxa.dream.mate.permission.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class PermissionInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "dream-mate-permission";
    public static final String PERMISSION_GET = "dream-mate-permission-get";
    public static final String PERMISSION_INJECT = "dream-mate-permission-inject";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case PERMISSION_GET:
                return new PermissionGetInvoker();
            case PERMISSION_INJECT:
                return new PermissionInjectInvoker();
        }
        return null;
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
