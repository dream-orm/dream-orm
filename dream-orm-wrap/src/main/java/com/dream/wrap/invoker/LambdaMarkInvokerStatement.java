package com.dream.wrap.invoker;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;

public class LambdaMarkInvokerStatement extends InvokerStatement {
    private Object value;

    public LambdaMarkInvokerStatement(Object value) {
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
        return Invoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return LambdaMarkInvoker.FUNCTION;
    }
}
