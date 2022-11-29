package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.util.InvokerUtil;

public class LogicInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement tenantStatement = InvokerUtil.wrapperInvoker(null,
                LogicInvoker.getName(), ",",
                statement.getStatement());
        statement.setStatement(tenantStatement);
    }
}
