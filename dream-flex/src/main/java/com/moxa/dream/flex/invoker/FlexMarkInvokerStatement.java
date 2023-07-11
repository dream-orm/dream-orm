package com.moxa.dream.flex.invoker;

import com.moxa.dream.antlr.smt.InvokerStatement;

public class FlexMarkInvokerStatement extends InvokerStatement {
    private Object value;

    public FlexMarkInvokerStatement(Object value) {
        this.value = value;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getNamespace() {
        return FlexMarkInvoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return FlexMarkInvoker.FUNCTION;
    }
}
