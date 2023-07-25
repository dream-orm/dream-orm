package com.moxa.dream.antlr.smt;

public class InsertStatement extends Statement {
    private Statement table;
    private Statement params;
    private Statement values;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    public Statement getParams() {
        return params;
    }

    public void setParams(Statement params) {
        this.params = wrapParent(params);
    }

    public Statement getValues() {
        return values;
    }

    public void setValues(Statement values) {
        this.values = wrapParent(values);
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
            this.statement = wrapParent(statement);
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(statement);
        }
    }
}
