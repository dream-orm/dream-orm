package com.dream.antlr.smt;

public abstract class EmitStatement extends Statement {
    protected Statement statement;

    public EmitStatement() {
    }

    public EmitStatement(Statement statement) {
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

    public static class ColonStatement extends EmitStatement {

    }

    public static class DollarStatement extends EmitStatement {

    }

    public static class SharpStatement extends EmitStatement {

    }
}
