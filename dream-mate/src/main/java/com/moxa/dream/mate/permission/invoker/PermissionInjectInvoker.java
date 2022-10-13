package com.moxa.dream.mate.permission.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.permission.handler.PermissionQueryHandler;
import com.moxa.dream.mate.permission.interceptor.PermissionHandler;
import com.moxa.dream.mate.permission.interceptor.PermissionInterceptor;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;

import java.util.List;

public class PermissionInjectInvoker extends AbstractInvoker {
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

    public static String getName() {
        return "dream_mate_permission_inject";
    }

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = methodInfo.getConfiguration();
        tableFactory = configuration.getTableFactory();
        PluginFactory pluginFactory = configuration.getPluginFactory();
        PermissionInterceptor permissionInterceptor = pluginFactory.getInterceptor(PermissionInterceptor.class);
        permissionHandler = permissionInterceptor.getPermissionHandler();

    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(invokerStatement.getParamStatement());
        return sql;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new PermissionQueryHandler(this)};
    }

    public boolean isPermissionInject(String table, int life) {
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        if (tableInfo == null) {
            return false;
        } else {
            return permissionHandler.isPermissionInject(methodInfo, tableInfo, life);
        }
    }
}
