package com.moxa.dream.mate.filter.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.filter.invoker.ColumnFilterInvokerFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public class ColumnFilterInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = InvokerUtil.wrapperInvoker(ColumnFilterInvokerFactory.NAMESPACE,
                ColumnFilterInvokerFactory.COLUMN_FILTER, ",",
                statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
