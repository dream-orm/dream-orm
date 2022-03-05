package com.moxa.dream.antlr.smt;

public class UpdateStatement extends Statement {
    private SymbolStatement.LetterStatement table;
    private Statement conditionList;
    private Statement where;

    public SymbolStatement.LetterStatement getTable() {
        return table;
    }

    public void setTable(SymbolStatement.LetterStatement table) {
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
        if (conditionList != null)
            conditionList.parentStatement = this;
    }

    public Statement getWhere() {
        return where;
    }

    public void setWhere(Statement where) {
        this.where = where;
        if (where != null)
            where.parentStatement = this;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, conditionList, where);
    }
}
