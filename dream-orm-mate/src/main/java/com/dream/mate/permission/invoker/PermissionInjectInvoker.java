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

import java.util.List;

public class PermissionInjectInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_permission_inject";
    private MethodInfo methodInfo;
    private PermissionHandler permissionHandler;

    public PermissionInjectInvoker() {

    }

    public PermissionInjectInvoker(MethodInfo methodInfo, PermissionHandler permissionHandler) {
        this.methodInfo = methodInfo;
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void init(Assist assist) {
        if (this.methodInfo == null) {
            this.methodInfo = assist.getCustom(MethodInfo.class);
        }
        if (this.permissionHandler == null) {
            Configuration configuration = assist.getCustom(Configuration.class);
            InjectFactory injectFactory = configuration.getInjectFactory();
            PermissionInject permissionInject = injectFactory.getInject(PermissionInject.class);
            this.permissionHandler = permissionInject.getPermissionHandler();
        }
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
        return permissionHandler.isPermissionInject(methodInfo, table);
    }
}
