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

    public static class AscStatement extends Statement {
        private final Statement statement;

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
    }

    public static class DescStatement extends Statement {
        private final Statement statement;

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
    }


}
