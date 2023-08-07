package com.dream.antlr.smt;

public class DeleteStatement extends Statement {
    private Statement table;
    private Statement where;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    public Statement getWhere() {
        return where;
    }

    public void setWhere(Statement where) {
        this.where = wrapParent(where);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, where);
    }
}
