package com.moxa.dream.antlr.smt;

public class DeleteStatement extends Statement {
    private SymbolStatement.LetterStatement table;
    private Statement condition;

    public SymbolStatement.LetterStatement getTable() {
        return table;
    }

    public void setTable(SymbolStatement.LetterStatement table) {
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
