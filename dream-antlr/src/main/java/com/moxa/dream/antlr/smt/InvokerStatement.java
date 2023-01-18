package com.moxa.dream.antlr.smt;

public class InvokerStatement extends Statement {
    private String namespace;
    private String function;
    private Statement paramStatement;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

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
        this.paramStatement = paramStatement;
        if (paramStatement != null) {
            paramStatement.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return false;
    }


}
