package com.dream.antlr.smt;

public class DeleteStatement extends Statement {
    private Statement table;
    private Statement where;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = table;
    }

    public Statement getWhere() {
        return where;
    }

    public void setWhere(Statement where) {
        this.where = where;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, where);
    }

    @Override
    public DeleteStatement clone() {
        DeleteStatement deleteStatement = (DeleteStatement) super.clone();
        deleteStatement.setTable(clone(table));
        deleteStatement.setWhere(clone(where));
        return deleteStatement;
    }
}
