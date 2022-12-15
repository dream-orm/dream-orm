package com.moxa.dream.mate.block.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
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
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory == null) {
            throw new DreamRunTimeException("不支持关键字拦截");
        }
        invokerFactory.addInvoker(new BlockInvoker());
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = AntlrUtil.invokerStatement(new BlockInvoker(), statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
