package com.dream.mate.transform.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.transform.invoker.TransformInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public class TransformInject implements Inject {
    private TransformHandler transformHandler;

    public TransformInject(TransformHandler transformHandler) {
        this.transformHandler = transformHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(TransformInvoker.FUNCTION) == null) {
            invokerFactory.addInvoker(TransformInvoker.FUNCTION, () -> new TransformInvoker(transformHandler));
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(TransformInvoker.FUNCTION, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public TransformHandler getTransformHandler() {
        return transformHandler;
    }
}
