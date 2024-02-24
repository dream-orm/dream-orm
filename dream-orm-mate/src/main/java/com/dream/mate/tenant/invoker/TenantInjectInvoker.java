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
import com.dream.system.config.MethodInfo;

import java.util.List;

public class TenantInjectInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_tenant_inject";
    private TenantHandler tenantHandler;

    public TenantInjectInvoker(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
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

    public boolean isTenant(Assist assist, String table) {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        return tenantHandler.isTenant(methodInfo, table);
    }

    public String getTenantColumn(String table) {
        return tenantHandler.getTenantColumn(table);
    }
}
