package com.dream.mate.version.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.version.inject.VersionHandler;
import com.dream.util.common.ObjectWrapper;

import java.util.List;

public class CurVersionGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_cur_version_get";
    private VersionHandler versionHandler;

    public CurVersionGetInvoker(VersionHandler versionHandler) {
        this.versionHandler = versionHandler;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        ObjectWrapper objectWrapper = assist.getCustom(ObjectWrapper.class);
        Object versionObject = versionHandler.getCurVersion(objectWrapper);
        return String.valueOf(versionObject);
    }

    @Override
    protected Handler[] handler() {
        return new Handler[0];
    }
}
