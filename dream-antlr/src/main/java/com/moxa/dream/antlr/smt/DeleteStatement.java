package com.moxa.dream.antlr.smt;

public class DeleteStatement extends Statement {
    private Statement table;
    private Statement condition;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = table;
        if (table != null)
            table.parentStatement = this;
    }

    public Statement getCondition() {
        return condition;
    }

    public void setCondition(Statement condition) {
        this.condition = condition;
        if (condition != null)
            condition.parentStatement = this;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, condition);
    }
}
