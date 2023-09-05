package com.dream.antlr.smt;

public class UpdateStatement extends Statement {
    private Statement table;
    private Statement conditionList;
    private Statement where;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    public Statement getConditionList() {
        return conditionList;
    }

    public void setConditionList(Statement conditionList) {
        this.conditionList = wrapParent(conditionList);
    }

    public Statement getWhere() {
        return where;
    }

    public void setWhere(Statement where) {
        this.where = wrapParent(where);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, conditionList, where);
    }

    @Override
    public UpdateStatement clone() {
        UpdateStatement updateStatement = (UpdateStatement) super.clone();
        updateStatement.table = clone(table);
        updateStatement.conditionList = clone(conditionList);
        updateStatement.where = clone(where);
        return updateStatement;
    }
}
