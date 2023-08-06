package com.dream.mate.permission.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.permission.inject.PermissionInject;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;

import java.util.List;

public class PermissionGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_permission_get";
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = assist.getCustom(Configuration.class);
        tableFactory = configuration.getTableFactory();
        InjectFactory injectFactory = configuration.getInjectFactory();
        PermissionInject permissionInject = injectFactory.getInject(PermissionInject.class);
        permissionHandler = permissionInject.getPermissionHandler();

    }

    @Override
    public Invoker newInstance() {
        return new PermissionGetInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String table = ((SymbolStatement.LetterStatement) columnList[0]).getValue();
        String alias = ((SymbolStatement.LetterStatement) columnList[1]).getValue();
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        return permissionHandler.getPermission(alias);
    }
}
