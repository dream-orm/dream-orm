package com.dream.antlr.smt;

public class TruncateTableStatement extends Statement {
    private Statement table;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table);
    }

    @Override
    public TruncateTableStatement clone() {
        TruncateTableStatement truncateTableStatement = (TruncateTableStatement) super.clone();
        truncateTableStatement.table = clone(table);
        return truncateTableStatement;
    }
}
