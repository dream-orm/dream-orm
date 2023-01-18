package com.moxa.dream.antlr.smt;


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
