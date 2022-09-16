package com.moxa.dream.system.inject;


import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.mapped.MethodInfo;

public class ScanInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE,
                AntlrInvokerFactory.SCAN, ",",
                statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
