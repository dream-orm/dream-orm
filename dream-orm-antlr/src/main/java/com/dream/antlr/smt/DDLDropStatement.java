package com.dream.antlr.smt;

public abstract class DDLDropStatement extends Statement {
    private Statement statement;

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

    public static class DDLDropDatabaseStatement extends DDLDropStatement {

    }

    public static class DDLDropTableStatement extends DDLDropStatement {

    }
}
