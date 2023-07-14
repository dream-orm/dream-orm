package com.moxa.dream.mate.tenant.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.invoker.TenantGetInvoker;
import com.moxa.dream.mate.tenant.invoker.TenantInjectInvoker;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

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
        InvokerStatement tenantStatement = AntlrUtil.invokerStatement(TenantInjectInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public TenantHandler getTenantHandler() {
        return tenantHandler;
    }
}
