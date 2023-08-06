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
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;

import java.util.List;

public class TenantInjectInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_tenant_inject";
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private TenantHandler tenantHandler;

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = assist.getCustom(Configuration.class);
        tableFactory = configuration.getTableFactory();
        InjectFactory injectFactory = configuration.getInjectFactory();
        TenantInject tenantInject = injectFactory.getInject(TenantInject.class);
        tenantHandler = tenantInject.getTenantHandler();

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
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        if (tableInfo != null) {
            return tenantHandler.isTenant(methodInfo, tableInfo);
        }
        return false;
    }

    public String getTenantColumn() {
        return tenantHandler.getTenantColumn();
    }
}
