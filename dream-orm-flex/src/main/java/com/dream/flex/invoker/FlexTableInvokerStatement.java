package com.dream.flex.invoker;

import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.SymbolStatement;

public class FlexTableInvokerStatement extends InvokerStatement implements SymbolStatement.Symbol {
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

    @Override
    public String getValue() {
        return table;
    }
}
