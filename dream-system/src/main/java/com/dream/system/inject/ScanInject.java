package com.dream.system.inject;


import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.config.MethodInfo;

public class ScanInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = AntlrUtil.invokerStatement(ScanInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
