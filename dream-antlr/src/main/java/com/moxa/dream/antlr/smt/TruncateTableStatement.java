package com.moxa.dream.antlr.smt;

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
}
