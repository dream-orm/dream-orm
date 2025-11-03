package com.dream.system.inject;


import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.config.MethodInfo;

public class ScanInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(ScanInvoker.FUNCTION) == null) {
            invokerFactory.addInvokers(new ScanInvoker());
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = AntlrUtil.invokerStatement(ScanInvoker.FUNCTION, statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
