package com.dream.mate.tenant.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.tenant.inject.TenantHandler;

import java.util.List;

public class TenantGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_tenant_get";
    private TenantHandler tenantHandler;

    public TenantGetInvoker(TenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
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
