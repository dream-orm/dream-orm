package com.dream.antlr.smt;

public class OrderStatement extends Statement {
    private Statement statement;

    public Statement getOrder() {
        return statement;
    }

    public void setOrder(Statement order) {
        this.statement = wrapParent(order);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    @Override
    public OrderStatement clone() {
        OrderStatement orderStatement = (OrderStatement) super.clone();
        orderStatement.statement = clone(statement);
        return orderStatement;
    }

    public static class AscStatement extends Statement {
        private Statement statement;

        public AscStatement(Statement statement) {
            this.statement = wrapParent(statement);
        }

        public Statement getSort() {
            return statement;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return true;
        }

        @Override
        public AscStatement clone() {
            AscStatement ascStatement = (AscStatement) super.clone();
            ascStatement.statement = clone(statement);
            return ascStatement;
        }
    }

    public static class DescStatement extends Statement {
        private Statement statement;

        public DescStatement(Statement statement) {
            this.statement = wrapParent(statement);
        }

        public Statement getSort() {
            return statement;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return true;
        }

        @Override
        public DescStatement clone() {
            DescStatement descStatement = (DescStatement) super.clone();
            descStatement.statement = clone(statement);
            return descStatement;
        }
    }


}
