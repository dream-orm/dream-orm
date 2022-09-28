package com.moxa.dream.mate.logic.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.logic.handler.LogicDeleteHandler;
import com.moxa.dream.mate.logic.handler.LogicQueryHandler;
import com.moxa.dream.mate.logic.handler.LogicUpdateHandler;
import com.moxa.dream.mate.logic.interceptor.LogicHandler;
import com.moxa.dream.mate.logic.interceptor.LogicInterceptor;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;

import java.util.List;

public class LogicInvoker extends AbstractInvoker {
    private TableFactory tableFactory;
    private MethodInfo methodInfo;
    private LogicHandler logicHandler;

    public static String getName() {
        return "dream-mate-logic-delete";
    }

    @Override
    public void init(Assist assist) {
        methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = methodInfo.getConfiguration();
        tableFactory = configuration.getTableFactory();
        PluginFactory pluginFactory = configuration.getPluginFactory();
        LogicInterceptor logicInterceptor = pluginFactory.getInterceptor(LogicInterceptor.class);
        logicHandler = logicInterceptor.getLogicHandler();

    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
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

    public String getNegativeValue() {
        return logicHandler.getNegativeValue();
    }

    public String getPositiveValue() {
        return logicHandler.getPositiveValue();
    }
}
