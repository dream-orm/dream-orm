package com.dream.system.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.EmitInvoker;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.config.MethodInfo;

public class EmitInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(EmitInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new EmitInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = AntlrUtil.invokerStatement(EmitInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
