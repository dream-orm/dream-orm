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

    @Override
    public DDLDropStatement clone() {
        DDLDropStatement ddlDropStatement = (DDLDropStatement) super.clone();
        ddlDropStatement.setStatement(clone(statement));
        return ddlDropStatement;
    }

    public static class DDLDropDatabaseStatement extends DDLDropStatement {

    }

    public static class DDLDropTableStatement extends DDLDropStatement {

    }
}
