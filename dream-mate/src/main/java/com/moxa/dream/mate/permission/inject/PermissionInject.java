package com.moxa.dream.mate.permission.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.permission.invoker.PermissionGetInvoker;
import com.moxa.dream.mate.permission.invoker.PermissionInjectInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

public class PermissionInject implements Inject {
    private PermissionHandler permissionHandler;

    public PermissionInject(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(PermissionInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new PermissionInjectInvoker());
        }
        if (invokerFactory.getInvoker(PermissionGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new PermissionGetInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = AntlrUtil.invokerStatement(PermissionInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
