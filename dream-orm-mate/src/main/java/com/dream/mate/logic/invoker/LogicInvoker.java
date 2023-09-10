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
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;

import java.util.List;

public class LogicInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_logic";
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private LogicHandler logicHandler;

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = assist.getCustom(Configuration.class);
        tableFactory = configuration.getTableFactory();
        InjectFactory injectFactory = configuration.getInjectFactory();
        LogicInject logicInject = injectFactory.getInject(LogicInject.class);
        logicHandler = logicInject.getLogicHandler();
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
        TableInfo tableInfo = tableFactory.getTableInfo(table);
        if (tableInfo == null) {
            return false;
        } else {
            return logicHandler.isLogic(methodInfo, tableInfo);
        }
    }

    public String getLogicColumn() {
        return logicHandler.getLogicColumn();
    }

    public String getDeletedValue() {
        return logicHandler.getDeletedValue();
    }
}
