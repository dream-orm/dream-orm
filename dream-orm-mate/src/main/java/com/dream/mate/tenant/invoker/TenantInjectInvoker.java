package com.dream.mate.tenant.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.tenant.handler.TenantDeleteHandler;
import com.dream.mate.tenant.handler.TenantInsertHandler;
import com.dream.mate.tenant.handler.TenantQueryHandler;
import com.dream.mate.tenant.handler.TenantUpdateHandler;
import com.dream.mate.tenant.inject.TenantHandler;
import com.dream.mate.tenant.inject.TenantInject;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;

import java.util.List;

public class TenantInjectInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_tenant_inject";
    private MethodInfo methodInfo;
    private TenantHandler tenantHandler;

    public TenantInjectInvoker() {

    }

    public TenantInjectInvoker(MethodInfo methodInfo, TenantHandler tenantHandler) {
        this.methodInfo = methodInfo;
        this.tenantHandler = tenantHandler;
    }

    @Override
    public void init(Assist assist) {
        if (this.methodInfo == null) {
            this.methodInfo = assist.getCustom(MethodInfo.class);
        }
        if (this.tenantHandler == null) {
            Configuration configuration = assist.getCustom(Configuration.class);
            InjectFactory injectFactory = configuration.getInjectFactory();
            TenantInject tenantInject = injectFactory.getInject(TenantInject.class);
            this.tenantHandler = tenantInject.getTenantHandler();
        }
    }

    @Override
    public Invoker newInstance() {
        return new TenantInjectInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(invokerStatement.getParamStatement());
        return sql;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new TenantQueryHandler(this), new TenantInsertHandler(this), new TenantUpdateHandler(this), new TenantDeleteHandler(this)};
    }

    public boolean isTenant(String table) {
        return tenantHandler.isTenant(methodInfo, table);
    }

    public String getTenantColumn() {
        return tenantHandler.getTenantColumn();
    }
}
