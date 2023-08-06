package com.dream.mate.permission.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.permission.handler.PermissionQueryHandler;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.permission.inject.PermissionInject;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;

import java.util.List;

public class PermissionInjectInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_permission_inject";
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = methodInfo.getConfiguration();
        tableFactory = configuration.getTableFactory();
        InjectFactory injectFactory = configuration.getInjectFactory();
        PermissionInject permissionInject = injectFactory.getInject(PermissionInject.class);
        permissionHandler = permissionInject.getPermissionHandler();

    }

    @Override
    public Invoker newInstance() {
        return new PermissionInjectInvoker();
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
        return new Handler[]{new PermissionQueryHandler(this)};
    }

    public boolean isPermissionInject(String table) {
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        if (tableInfo == null) {
            return false;
        } else {
            return permissionHandler.isPermissionInject(methodInfo, tableInfo);
        }
    }
}
