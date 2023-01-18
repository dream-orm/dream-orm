package com.moxa.dream.antlr.smt;

public class UpdateStatement extends Statement {
    private Statement table;
    private Statement conditionList;
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

    public Statement getConditionList() {
        return conditionList;
    }

    public void setConditionList(Statement conditionList) {
        this.conditionList = conditionList;
        if (conditionList != null) {
            conditionList.parentStatement = this;
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
        return isNeedInnerCache(table, conditionList, where);
    }
}
