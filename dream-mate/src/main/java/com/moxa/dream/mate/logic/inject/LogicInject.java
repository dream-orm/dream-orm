package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class LogicInject implements Inject {
    private LogicHandler logicHandler;

    public LogicInject(LogicHandler logicHandler) {
        this.logicHandler = logicHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        DialectFactory dialectFactory = methodInfo.getConfiguration().getDialectFactory();
        if (!(dialectFactory instanceof DefaultDialectFactory)) {
            throw new DreamRunTimeException("不支持逻辑删除");
        }
        DefaultInvokerFactory invokerFactory = ((DefaultDialectFactory) dialectFactory).getInvokerFactory(DefaultInvokerFactory.class);
        if (invokerFactory == null) {
            throw new DreamRunTimeException("不支持逻辑删除");
        }
        String invokerName = LogicInvoker.getName();
        invokerFactory.addInvoker(invokerName, new LogicInvoker());
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(null,
                invokerName, ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public LogicHandler getLogicHandler() {
        return logicHandler;
    }
}
