package com.moxa.dream.mate.permission.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class PermissionInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "permission";
    public static final String PERMISSION_GET = "permissionGet";
    public static final String PERMISSION_INJECT = "permissionInject";

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
