package com.moxa.dream.mate.block.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.mate.block.invoker.BlockInvoker;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class BlockInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        DialectFactory dialectFactory = methodInfo.getConfiguration().getDialectFactory();
        if (!(dialectFactory instanceof DefaultDialectFactory)) {
            throw new DreamRunTimeException("不支持关键字拦截");
        }
        DefaultInvokerFactory invokerFactory = ((DefaultDialectFactory) dialectFactory).getInvokerFactory(DefaultInvokerFactory.class);
        if (invokerFactory == null) {
            throw new DreamRunTimeException("不支持关键字拦截");
        }
        String invokerName = BlockInvoker.getName();
        invokerFactory.addInvoker(invokerName, new BlockInvoker());
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = InvokerUtil.wrapperInvoker(null,
                invokerName, ",",
                statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
