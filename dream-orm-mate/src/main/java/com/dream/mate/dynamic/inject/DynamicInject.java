package com.dream.mate.dynamic.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.dynamic.invoker.DynamicGetInvoker;
import com.dream.mate.dynamic.invoker.DynamicInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public class DynamicInject implements Inject {
    private DynamicHandler dynamicHandler;

    public DynamicInject(DynamicHandler dynamicHandler) {
        this.dynamicHandler = dynamicHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(DynamicInvoker.FUNCTION) == null) {
            invokerFactory.addInvokers(new DynamicInvoker(dynamicHandler));
        }
        if (invokerFactory.getInvoker(DynamicGetInvoker.FUNCTION) == null) {
            invokerFactory.addInvokers(new DynamicGetInvoker(dynamicHandler));
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(DynamicInvoker.FUNCTION, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public DynamicHandler getDynamicHandler() {
        return dynamicHandler;
    }
}
