package com.moxa.dream.mate.permission.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.permission.interceptor.PermissionHandler;
import com.moxa.dream.mate.permission.interceptor.PermissionInterceptor;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;

import java.util.List;

public class PermissionGetInvoker extends AbstractInvoker {
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

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
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String table = ((SymbolStatement.LetterStatement) columnList[0]).getValue();
        String alias = ((SymbolStatement.LetterStatement) columnList[1]).getValue();
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        return permissionHandler.getPermission(methodInfo, tableInfo, alias);
    }
}
