package com.moxa.dream.mate.tenant.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.interceptor.TenantInterceptor;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class TenantInject implements Inject {
    private TenantHandler tenantHandler;

    public TenantInject(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        Configuration configuration = methodInfo.getConfiguration();
        InvokerFactory invokerFactory = configuration.getInvokerFactory();
        PluginFactory pluginFactory = configuration.getPluginFactory();
        if (pluginFactory == null) {
            throw new DreamRunTimeException("多租户模式，请开启插件");
        }
        TenantInterceptor tenantInterceptor = pluginFactory.getInterceptor(TenantInterceptor.class);
        if (tenantInterceptor == null) {
            throw new DreamRunTimeException("多租户模式，请开启插件" + TenantInterceptor.class.getName());
        }
        if (invokerFactory.getInvoker(TenantInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            throw new DreamRunTimeException("多租户模式，请开启函数@" + TenantInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = AntlrUtil.invokerStatement(TenantInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public TenantHandler getTenantHandler() {
        return tenantHandler;
    }
}
