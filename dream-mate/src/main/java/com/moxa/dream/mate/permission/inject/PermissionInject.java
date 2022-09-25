package com.moxa.dream.mate.permission.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.permission.invoker.PermissionInvokerFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public class PermissionInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(PermissionInvokerFactory.NAMESPACE,
                PermissionInvokerFactory.PERMISSION_INJECT, ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }
}
