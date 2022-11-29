package com.moxa.dream.system.inject;


import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.util.InvokerUtil;

public class ScanInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement scanStatement = InvokerUtil.wrapperInvoker(SystemInvokerFactory.NAMESPACE,
                SystemInvokerFactory.SCAN, ",",
                statement.getStatement());
        statement.setStatement(scanStatement);
    }
}
