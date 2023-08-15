package com.dream.mate.transform.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
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
        if (invokerFactory.getInvoker(TransformInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new TransformInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = AntlrUtil.invokerStatement(TransformInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }

    public TransformHandler getTransformHandler() {
        return transformHandler;
    }
}
