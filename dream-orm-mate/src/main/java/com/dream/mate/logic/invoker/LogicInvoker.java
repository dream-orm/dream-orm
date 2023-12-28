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
import com.dream.mate.logic.inject.LogicInject;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;

import java.util.List;

public class LogicInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_logic";
    private MethodInfo methodInfo;
    private LogicHandler logicHandler;

    public LogicInvoker() {

    }

    public LogicInvoker(MethodInfo methodInfo, LogicHandler logicHandler) {
        this.methodInfo = methodInfo;
        this.logicHandler = logicHandler;
    }

    @Override
    public void init(Assist assist) {
        if (this.methodInfo == null) {
            this.methodInfo = assist.getCustom(MethodInfo.class);
        }
        if (this.logicHandler == null) {
            Configuration configuration = assist.getCustom(Configuration.class);
            InjectFactory injectFactory = configuration.getInjectFactory();
            LogicInject logicInject = injectFactory.getInject(LogicInject.class);
            this.logicHandler = logicInject.getLogicHandler();
        }
    }

    @Override
    public Invoker newInstance() {
        return new LogicInvoker();
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

    public boolean isLogicDelete(String table) {
        return logicHandler.isLogic(methodInfo, table);
    }

    public String getLogicColumn() {
        return logicHandler.getLogicColumn();
    }

    public String getDeletedValue() {
        return logicHandler.getDeletedValue();
    }

    public String getNormalValue() {
        return logicHandler.getNormalValue();
    }
}
