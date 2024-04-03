package com.dream.struct.invoker;

import com.dream.antlr.smt.InvokerStatement;

public class TakeMarkInvokerStatement extends InvokerStatement {
    private Object value;

    public TakeMarkInvokerStatement(Object value) {
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
        return TakeMarkInvoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return TakeMarkInvoker.FUNCTION;
    }
}
