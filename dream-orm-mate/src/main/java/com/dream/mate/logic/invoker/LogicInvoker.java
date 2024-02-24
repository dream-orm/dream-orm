package com.dream.mate.logic.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.logic.handler.LogicDeleteHandler;
import com.dream.mate.logic.handler.LogicQueryHandler;
import com.dream.mate.logic.handler.LogicUpdateHandler;
import com.dream.mate.logic.inject.LogicHandler;
import com.dream.system.config.MethodInfo;

import java.util.List;

public class LogicInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_logic";
    private LogicHandler logicHandler;

    public LogicInvoker(LogicHandler logicHandler) {
        this.logicHandler = logicHandler;
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
        return new Handler[]{new LogicQueryHandler(this), new LogicDeleteHandler(this), new LogicUpdateHandler(this)};
    }

    public boolean isLogicDelete(Assist assist, String table) {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        return logicHandler.isLogic(methodInfo, table);
    }

    public String getLogicColumn(String table) {
        return logicHandler.getLogicColumn(table);
    }

    public String getDeletedValue() {
        return logicHandler.getDeletedValue();
    }

    public String getNormalValue() {
        return logicHandler.getNormalValue();
    }
}
