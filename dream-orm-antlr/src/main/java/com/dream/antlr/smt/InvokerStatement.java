package com.dream.antlr.smt;

public class InvokerStatement extends Statement {
    private String function;
    private Statement paramStatement;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Statement getParamStatement() {
        return paramStatement;
    }

    public void setParamStatement(Statement paramStatement) {
        this.paramStatement = wrapParent(paramStatement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return false;
    }

    @Override
    public InvokerStatement clone() {
        InvokerStatement invokerStatement = (InvokerStatement) super.clone();
        invokerStatement.setFunction(function);
        invokerStatement.setParamStatement(clone(paramStatement));
        return invokerStatement;
    }
}
