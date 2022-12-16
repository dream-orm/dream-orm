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
import com.moxa.dream.util.exception.DreamRunTimeException;

public class PermissionInject implements Inject {
    private PermissionHandler permissionHandler;

    public PermissionInject(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(PermissionInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            throw new DreamRunTimeException("权限模式，请开启函数@" + PermissionInjectInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
        }
        if (invokerFactory.getInvoker(PermissionGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            throw new DreamRunTimeException("权限模式，请开启函数@" + PermissionGetInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = AntlrUtil.invokerStatement(PermissionInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
