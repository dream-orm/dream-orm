package com.dream.flex.invoker;

import com.dream.antlr.smt.InvokerStatement;

public class FlexTableInvokerStatement extends InvokerStatement {
    private String table;

    public FlexTableInvokerStatement(String table) {
        this.table = table;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    @Override
    public String getNamespace() {
        return FlexTableInvoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return FlexTableInvoker.FUNCTION;
    }

    public String getTable() {
        return table;
    }
}
