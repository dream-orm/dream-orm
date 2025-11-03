package com.dream.struct.invoker;

import com.dream.antlr.smt.InvokerStatement;

public class TakeColumnInvokerStatement extends InvokerStatement {
    private Class entityType;

    public TakeColumnInvokerStatement(Class entityType) {
        this.entityType = entityType;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    @Override
    public String getFunction() {
        return TakeColumnInvoker.FUNCTION;
    }

    public Class getEntityType() {
        return entityType;
    }
}
