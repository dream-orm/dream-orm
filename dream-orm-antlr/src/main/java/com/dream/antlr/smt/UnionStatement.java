package com.dream.antlr.smt;

public class UnionStatement extends Statement {
    private boolean all;
    private Statement statement;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
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
    public UnionStatement clone() {
        UnionStatement unionStatement = (UnionStatement) super.clone();
        unionStatement.setAll(all);
        unionStatement.setStatement(clone(statement));
        return unionStatement;
    }
}
