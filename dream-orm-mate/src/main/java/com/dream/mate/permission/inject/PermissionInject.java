package com.dream.mate.permission.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.permission.invoker.PermissionGetInvoker;
import com.dream.mate.permission.invoker.PermissionInjectInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public class PermissionInject implements Inject {
    private PermissionHandler permissionHandler;

    public PermissionInject(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(PermissionInjectInvoker.FUNCTION) == null) {
            invokerFactory.addInvoker(PermissionInjectInvoker.FUNCTION, () -> new PermissionInjectInvoker(permissionHandler));
        }
        if (invokerFactory.getInvoker(PermissionGetInvoker.FUNCTION) == null) {
            invokerFactory.addInvoker(PermissionGetInvoker.FUNCTION, () -> new PermissionGetInvoker(permissionHandler));
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(PermissionInjectInvoker.FUNCTION, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
