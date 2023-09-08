package com.dream.antlr.smt;

public abstract class EmitStatement extends Statement {
    protected Statement statement;

    public EmitStatement() {
    }

    public EmitStatement(Statement statement) {
        this.statement = wrapParent(statement);
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
    public EmitStatement clone() {
        EmitStatement emitStatement = (EmitStatement) super.clone();
        emitStatement.setStatement(clone(statement));
        return emitStatement;
    }

    public static class ColonStatement extends EmitStatement {

    }

    public static class DollarStatement extends EmitStatement {

    }

    public static class SharpStatement extends EmitStatement {

    }
}
