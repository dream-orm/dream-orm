package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.logic.invoker.LogicInvokerFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public class LogicInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(LogicInvokerFactory.NAMESPACE,
                LogicInvokerFactory.LOGIC_DELETE, ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }
}
