package com.moxa.dream.mate.tenant.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvokerFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public class TenantInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = InvokerUtil.wrapperInvoker(TenantInvokerFactory.NAMESPACE,
                TenantInvokerFactory.TENANT, ",",
                statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
