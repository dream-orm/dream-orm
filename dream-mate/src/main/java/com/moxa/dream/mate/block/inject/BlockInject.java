package com.moxa.dream.mate.block.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.block.invoker.BlockInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class BlockInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(BlockInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            throw new DreamRunTimeException("关键字拦截模式，请开启函数@" + BlockInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = AntlrUtil.invokerStatement(BlockInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
