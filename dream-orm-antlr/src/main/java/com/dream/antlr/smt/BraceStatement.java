package com.dream.antlr.smt;


public class BraceStatement extends Statement {
    private Statement statement;

    public BraceStatement() {

    }

    public BraceStatement(Statement statement) {
        setStatement(statement);
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = wrapParent(statement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    @Override
    public BraceStatement clone() {
        BraceStatement braceStatement = (BraceStatement) super.clone();
        braceStatement.setStatement(clone(statement));
        return braceStatement;
    }
}
