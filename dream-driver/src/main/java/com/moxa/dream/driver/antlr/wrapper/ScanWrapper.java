package com.moxa.dream.driver.antlr.wrapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.mapper.MethodInfo;

public class ScanWrapper implements Wrapper {
    @Override
    public void wrapper(MethodInfo methodInfo) {
        PackageStatement packageStatement = methodInfo.getStatement();
        InvokerStatement scanStatement = InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE,
                AntlrInvokerFactory.SCAN, ",",
                packageStatement.getStatement());
        packageStatement.setStatement(scanStatement);
    }
}
