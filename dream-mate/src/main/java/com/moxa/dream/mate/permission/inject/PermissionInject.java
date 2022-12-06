package com.moxa.dream.mate.permission.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.mate.permission.invoker.PermissionGetInvoker;
import com.moxa.dream.mate.permission.invoker.PermissionInjectInvoker;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class PermissionInject implements Inject {
    private PermissionHandler permissionHandler;

    public PermissionInject(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        DialectFactory dialectFactory = methodInfo.getConfiguration().getDialectFactory();
        if (!(dialectFactory instanceof DefaultDialectFactory)) {
            throw new DreamRunTimeException("不支持数据权限");
        }
        DefaultInvokerFactory invokerFactory = ((DefaultDialectFactory) dialectFactory).getInvokerFactory(DefaultInvokerFactory.class);
        if (invokerFactory == null) {
            throw new DreamRunTimeException("不支持数据权限");
        }
        String invokerName = PermissionInjectInvoker.getName();
        invokerFactory.addInvoker(invokerName, new PermissionInjectInvoker());
        invokerFactory.addInvoker(PermissionGetInvoker.getName(), new PermissionGetInvoker());

        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(null,
                invokerName, ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
