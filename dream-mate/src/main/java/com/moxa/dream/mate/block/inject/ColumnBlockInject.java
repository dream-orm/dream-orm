package com.moxa.dream.mate.block.inject;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.block.invoker.ColumnBlockInvokerFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public class ColumnBlockInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        PackageStatement statement = methodInfo.getStatement();
        InvokerStatement columnFilterStatement = InvokerUtil.wrapperInvoker(ColumnBlockInvokerFactory.NAMESPACE,
                ColumnBlockInvokerFactory.COLUMN_BLOCK, ",",
                statement.getStatement());
        statement.setStatement(columnFilterStatement);
    }
}
