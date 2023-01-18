package com.moxa.dream.antlr.smt;

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
        this.statement = statement;
        if (statement != null) {
            statement.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }
}
