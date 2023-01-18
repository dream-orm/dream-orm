package com.moxa.dream.antlr.smt;

public class DeleteStatement extends Statement {
    private Statement table;
    private Statement where;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = table;
        if (table != null) {
            table.parentStatement = this;
        }
    }

    public Statement getWhere() {
        return where;
    }

    public void setWhere(Statement where) {
        this.where = where;
        if (where != null) {
            where.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, where);
    }
}
