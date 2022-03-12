package com.moxa.dream.module.wrapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.module.mapper.MethodInfo;

public class ScanWrapper implements Wrapper {
    @Override
    public void wrapper(PackageStatement packageStatement, MethodInfo methodInfo) {
        InvokerStatement scanStatement = InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE,
                AntlrInvokerFactory.SCAN, ",",
                packageStatement.getStatement());
        packageStatement.setStatement(scanStatement);
    }
}