package com.moxa.dream.mate.tenant.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.mate.tenant.interceptor.TenantInterceptor;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class TenantInject implements Inject {
    private TenantHandler tenantHandler;

    public TenantInject(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        Configuration configuration = methodInfo.getConfiguration();
        DialectFactory dialectFactory = configuration.getDialectFactory();
        if (!(dialectFactory instanceof DefaultDialectFactory)) {
            throw new DreamRunTimeException("不支持多租户");
        }
        DefaultInvokerFactory invokerFactory = ((DefaultDialectFactory) dialectFactory).getInvokerFactory(DefaultInvokerFactory.class);
        if (invokerFactory == null) {
            throw new DreamRunTimeException("不支持多租户");
        }
        PluginFactory pluginFactory = configuration.getPluginFactory();
        if (pluginFactory == null) {
            throw new DreamRunTimeException("插件工厂未开启，不支持多租户");
        }
        pluginFactory.interceptor(new TenantInterceptor(tenantHandler));
        String invokerName = TenantInvoker.getName();
        invokerFactory.addInvoker(invokerName, new TenantInvoker());
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(null,
                invokerName, ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public TenantHandler getTenantHandler() {
        return tenantHandler;
    }
}
