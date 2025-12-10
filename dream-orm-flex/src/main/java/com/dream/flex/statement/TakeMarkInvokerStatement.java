package com.dream.flex.statement;

import com.dream.antlr.smt.InvokerStatement;
import com.dream.flex.invoker.TakeMarkInvoker;

public class TakeMarkInvokerStatement extends InvokerStatement {
    private String column;
    private Object value;

    public TakeMarkInvokerStatement(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getFunction() {
        return TakeMarkInvoker.FUNCTION;
    }
}
