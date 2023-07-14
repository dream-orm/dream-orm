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

import java.util.Set;

public class BlockInject implements Inject {
    private BlockInvoker blockInvoker;

    public BlockInject() {

    }

    public BlockInject(String resource) {
        blockInvoker = new BlockInvoker(resource);
    }

    public BlockInject(Set<String> filterSet) {
        blockInvoker = new BlockInvoker(filterSet);
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(BlockInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            if (blockInvoker == null) {
                throw new DreamRunTimeException("关键字拦截模式，请开启函数@" + BlockInvoker.FUNCTION + ":" + Invoker.DEFAULT_NAMESPACE);
            } else {
                invokerFactory.addInvokers(blockInvoker);
            }
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = AntlrUtil.invokerStatement(BlockInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
