package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

public class LogicInject implements Inject {
    private LogicHandler logicHandler;

    public LogicInject(LogicHandler logicHandler) {
        this.logicHandler = logicHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(LogicInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new LogicInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement logicStatement = AntlrUtil.invokerStatement(LogicInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(logicStatement);
    }

    public LogicHandler getLogicHandler() {
        return logicHandler;
    }
}
