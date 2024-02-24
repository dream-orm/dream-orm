package com.dream.mate.dynamic.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.dynamic.handler.DynamicDeleteHandler;
import com.dream.mate.dynamic.handler.DynamicInsertHandler;
import com.dream.mate.dynamic.handler.DynamicQueryHandler;
import com.dream.mate.dynamic.handler.DynamicUpdateHandler;
import com.dream.mate.dynamic.inject.DynamicHandler;
import com.dream.system.config.MethodInfo;

import java.util.List;

public class DynamicInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_dynamic";
    private DynamicHandler dynamicHandler;

    public DynamicInvoker(DynamicHandler dynamicHandler) {
        this.dynamicHandler = dynamicHandler;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(invokerStatement.getParamStatement());
        return sql;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new DynamicQueryHandler(this), new DynamicUpdateHandler(this), new DynamicInsertHandler(this), new DynamicDeleteHandler(this)};
    }

    public boolean isDynamic(Assist assist, String table) {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        return dynamicHandler.isDynamic(methodInfo, table);
    }
}
