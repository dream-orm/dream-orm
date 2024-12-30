package com.dream.antlr.smt;

public class FuncOverStatement extends Statement {
    private Statement functionStatement;
    private Statement overStatement;

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(functionStatement, overStatement);
    }

    public Statement getFunctionStatement() {
        return functionStatement;
    }

    public void setFunctionStatement(Statement functionStatement) {
        this.functionStatement = wrapParent(functionStatement);
    }

    public Statement getOverStatement() {
        return overStatement;
    }

    public void setOverStatement(Statement overStatement) {
        this.overStatement = wrapParent(overStatement);
    }

    @Override
    public Statement clone() {
        FuncOverStatement funcOverStatement = (FuncOverStatement) super.clone();
        funcOverStatement.setFunctionStatement(clone(functionStatement));
        funcOverStatement.setOverStatement(clone(overStatement));
        return funcOverStatement;
    }
}
