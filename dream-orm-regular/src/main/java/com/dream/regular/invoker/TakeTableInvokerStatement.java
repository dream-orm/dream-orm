package com.dream.regular.invoker;

import com.dream.antlr.smt.InvokerStatement;

public class TakeTableInvokerStatement extends InvokerStatement {
    private String table;

    public TakeTableInvokerStatement(String table) {
        this.table = table;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    @Override
    public String getNamespace() {
        return TakeTableInvoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return TakeTableInvoker.FUNCTION;
    }

    public String getTable() {
        return table;
    }
}
