package com.moxa.dream.system.inject;


import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;
import com.moxa.dream.system.config.MethodInfo;

public class ScanInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = AntlrUtil.invokerStatement(ScanInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
