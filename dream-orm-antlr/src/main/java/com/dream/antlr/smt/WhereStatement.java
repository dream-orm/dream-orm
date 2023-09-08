package com.dream.antlr.smt;

public class WhereStatement extends Statement {
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
    public WhereStatement clone() {
        WhereStatement whereStatement = (WhereStatement) super.clone();
        whereStatement.setStatement(clone(statement));
        return whereStatement;
    }
}
