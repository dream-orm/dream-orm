package com.dream.mate.tenant.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.tenant.invoker.TenantGetInvoker;
import com.dream.mate.tenant.invoker.TenantInjectInvoker;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public class TenantInject implements Inject {
    private TenantHandler tenantHandler;

    public TenantInject(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        Configuration configuration = methodInfo.getConfiguration();
        InvokerFactory invokerFactory = configuration.getInvokerFactory();
        if (invokerFactory.getInvoker(TenantInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new TenantInjectInvoker());
        }
        if (invokerFactory.getInvoker(TenantGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new TenantGetInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(TenantInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public TenantHandler getTenantHandler() {
        return tenantHandler;
    }
}
