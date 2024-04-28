package com.dream.antlr.smt;

public class InsertStatement extends Statement {
    private boolean ignore = false;
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

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, columns, values);
    }

    @Override
    public InsertStatement clone() {
        InsertStatement insertStatement = (InsertStatement) super.clone();
        insertStatement.setTable(clone(table));
        insertStatement.setColumns(clone(columns));
        insertStatement.setValues(clone(values));
        insertStatement.setIgnore(ignore);
        return insertStatement;
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

        @Override
        public ValuesStatement clone() {
            ValuesStatement valuesStatement = (ValuesStatement) super.clone();
            valuesStatement.setStatement(clone(statement));
            return valuesStatement;
        }
    }
}
