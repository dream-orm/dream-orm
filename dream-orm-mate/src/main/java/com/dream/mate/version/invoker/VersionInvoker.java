package com.dream.mate.version.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.version.handler.VersionUpdateHandler;
import com.dream.mate.version.inject.VersionHandler;
import com.dream.system.config.MethodInfo;

import java.util.List;

public class VersionInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_version";
    private MethodInfo methodInfo;
    private VersionHandler versionHandler;

    public VersionInvoker(VersionHandler versionHandler) {
        this.versionHandler = versionHandler;
    }

    @Override
    public void init(Assist assist) {
        this.methodInfo = assist.getCustom(MethodInfo.class);
    }

    @Override
    public Invoker newInstance() {
        return new VersionInvoker(versionHandler);
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
        return new Handler[]{new VersionUpdateHandler(this)};
    }

    public boolean isVersion(String table) {
        return versionHandler.isVersion(methodInfo, table);
    }


    public String getVersionColumn() {
        return versionHandler.getVersionColumn();
    }
}
