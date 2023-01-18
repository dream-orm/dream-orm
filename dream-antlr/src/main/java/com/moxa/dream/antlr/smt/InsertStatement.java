package com.moxa.dream.antlr.smt;

public class InsertStatement extends Statement {
    private Statement table;
    private Statement params;
    private Statement values;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = table;
        if (table != null) {
            table.parentStatement = this;
        }
    }

    public Statement getParams() {
        return params;
    }

    public void setParams(Statement params) {
        this.params = params;
        if (params != null) {
            params.parentStatement = this;
        }
    }

    public Statement getValues() {
        return values;
    }

    public void setValues(Statement values) {
        this.values = values;
        if (values != null) {
            values.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(table, params, values);
    }

    public static class ValuesStatement extends Statement {
        private Statement statement;

        public Statement getStatement() {
            return statement;
        }

        public void setStatement(Statement statement) {
            this.statement = statement;
            this.statement.parentStatement = this;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(statement);
        }
    }
}
