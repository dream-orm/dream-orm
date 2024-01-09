package com.dream.mate.version.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.version.invoker.CurVersionGetInvoker;
import com.dream.mate.version.invoker.NextVersionGetInvoker;
import com.dream.mate.version.invoker.VersionInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public class VersionInject implements Inject {
    private VersionHandler versionHandler;

    public VersionInject(VersionHandler versionHandler) {
        this.versionHandler = versionHandler;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(VersionInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new VersionInvoker(versionHandler));
        }
        if (invokerFactory.getInvoker(CurVersionGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new CurVersionGetInvoker(versionHandler));
        }
        if (invokerFactory.getInvoker(NextVersionGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new NextVersionGetInvoker(versionHandler));
        }
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(VersionInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, statement.getStatement());
        statement.setStatement(invokerStatement);
    }

    public VersionHandler getLogicHandler() {
        return versionHandler;
    }
}
