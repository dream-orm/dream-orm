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
import com.dream.system.inject.factory.InjectFactory;

import java.util.List;

public class PermissionGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_permission_get";
    private PermissionHandler permissionHandler;

    public PermissionGetInvoker() {

    }

    public PermissionGetInvoker(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void init(Assist assist) {
        if (this.permissionHandler == null) {
            Configuration configuration = assist.getCustom(Configuration.class);
            InjectFactory injectFactory = configuration.getInjectFactory();
            PermissionInject permissionInject = injectFactory.getInject(PermissionInject.class);
            this.permissionHandler = permissionInject.getPermissionHandler();
        }
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
        String alias = ((SymbolStatement.LetterStatement) columnList[1]).getValue();
        return permissionHandler.getPermission(alias);
    }
}
