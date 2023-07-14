package com.moxa.dream.mate.tenant.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.tenant.inject.TenantHandler;
import com.moxa.dream.mate.tenant.inject.TenantInject;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.inject.factory.InjectFactory;

import java.util.List;

public class TenantGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_tenant_get";
    private TenantHandler tenantHandler;

    @Override
    public void init(Assist assist) {
        Configuration configuration = assist.getCustom(Configuration.class);
        InjectFactory injectFactory = configuration.getInjectFactory();
        TenantInject tenantInject = injectFactory.getInject(TenantInject.class);
        tenantHandler = tenantInject.getTenantHandler();

    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Object tenantObject = tenantHandler.getTenantObject();
        if (tenantObject instanceof Number) {
            return String.valueOf(tenantObject);
        } else {
            return "'" + tenantObject + "'";
        }
    }

    @Override
    protected Handler[] handler() {
        return new Handler[0];
    }
}
