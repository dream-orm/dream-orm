package com.moxa.dream.mate.permission.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.permission.inject.PermissionHandler;
import com.moxa.dream.mate.permission.inject.PermissionInject;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;

import java.util.List;

public class PermissionGetInvoker extends AbstractInvoker {
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

    public static String getName() {
        return "dream_mate_permission_get";
    }

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
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String table = ((SymbolStatement.LetterStatement) columnList[0]).getValue();
        String alias = ((SymbolStatement.LetterStatement) columnList[1]).getValue();
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        return permissionHandler.getPermission(methodInfo, tableInfo, alias);
    }
}
