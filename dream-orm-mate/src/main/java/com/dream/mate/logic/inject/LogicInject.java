package com.dream.mate.logic.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.logic.invoker.LogicInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

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
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(LogicInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public LogicHandler getLogicHandler() {
        return logicHandler;
    }
}
