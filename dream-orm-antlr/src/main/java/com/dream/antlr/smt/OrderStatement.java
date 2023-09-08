package com.dream.antlr.smt;

public class OrderStatement extends Statement {
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
    public OrderStatement clone() {
        OrderStatement orderStatement = (OrderStatement) super.clone();
        orderStatement.setStatement(clone(statement));
        return orderStatement;
    }

    public static class AscStatement extends Statement {
        private Statement statement;

        public AscStatement(Statement statement) {
            setStatement(statement);
        }

        public void setStatement(Statement statement) {
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
            ascStatement.setStatement(clone(statement));
            return ascStatement;
        }
    }

    public static class DescStatement extends Statement {
        private Statement statement;

        public DescStatement(Statement statement) {
            setStatement(statement);
        }

        public void setStatement(Statement statement) {
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
            descStatement.setStatement(clone(statement));
            return descStatement;
        }
    }


}
