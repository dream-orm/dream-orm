package com.dream.antlr.smt;

public class ReplaceIntoStatement extends Statement {
    private Statement table;
    private Statement columns;
    private Statement values;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    public Statement getColumns() {
        return columns;
    }

    public void setColumns(Statement columns) {
        this.columns = wrapParent(columns);
    }

    public Statement getValues() {
        return values;
    }

    public void setValues(Statement values) {
        this.values = wrapParent(values);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, columns, values);
    }

    @Override
    public ReplaceIntoStatement clone() {
        ReplaceIntoStatement insertStatement = (ReplaceIntoStatement) super.clone();
        insertStatement.setTable(clone(table));
        insertStatement.setColumns(clone(columns));
        insertStatement.setValues(clone(values));
        return insertStatement;
    }
}
