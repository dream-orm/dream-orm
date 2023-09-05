package com.dream.antlr.smt;

public class SharpStatement extends Statement {
    protected Statement statement;

    public SharpStatement() {
    }

    public SharpStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    @Override
    public SharpStatement clone() {
        SharpStatement sharpStatement = (SharpStatement) super.clone();
        sharpStatement.statement = clone(statement);
        return sharpStatement;
    }
}
