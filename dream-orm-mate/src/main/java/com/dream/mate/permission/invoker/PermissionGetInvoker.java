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

import java.util.List;

public class PermissionGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_permission_get";
    protected PermissionHandler permissionHandler;

    public PermissionGetInvoker(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String alias = ((SymbolStatement.LetterStatement) columnList[0]).getValue();
        return permissionHandler.getPermission(alias);
    }
}
