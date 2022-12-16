package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class LogicInject implements Inject {
    private LogicHandler logicHandler;

    public LogicInject(LogicHandler logicHandler) {
        this.logicHandler = logicHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(LogicInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            throw new DreamRunTimeException("逻辑删除模式，请开启函数@" + LogicInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = AntlrUtil.invokerStatement(LogicInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(tenantStatement);
    }

    public LogicHandler getLogicHandler() {
        return logicHandler;
    }
}
