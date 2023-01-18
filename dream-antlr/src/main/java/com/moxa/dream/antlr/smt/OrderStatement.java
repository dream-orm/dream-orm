package com.moxa.dream.antlr.smt;

public class OrderStatement extends Statement {
    private Statement statement;

    public Statement getOrder() {
        return statement;
    }

    public void setOrder(Statement order) {
        this.statement = order;
        if (statement != null) {
            order.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public static class AscStatement extends Statement {
        private final Statement statement;

        public AscStatement(Statement statement) {
            this.statement = statement;
            if (statement != null) {
                statement.parentStatement = this;
            }

        }

        public Statement getSort() {
            return statement;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return true;
        }
    }

    public static class DescStatement extends Statement {
        private final Statement statement;

        public DescStatement(Statement statement) {
            this.statement = statement;
            if (statement != null) {
                statement.parentStatement = this;
            }
        }

        public Statement getSort() {
            return statement;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return true;
        }
    }


}
