package com.dream.antlr.smt;

public class InsertStatement extends Statement {
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

    public static class ValuesStatement extends Statement {
        private Statement statement;

        public Statement getStatement() {
            return statement;
        }

        public void setStatement(Statement statement) {
            this.statement = wrapParent(statement);
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(statement);
        }
    }
}
