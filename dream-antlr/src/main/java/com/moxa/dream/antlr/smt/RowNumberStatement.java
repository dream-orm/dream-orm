package com.moxa.dream.antlr.smt;

public class RowNumberStatement extends Statement {
    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        if (statement != null) {
            statement.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public static class OverStatement extends Statement {
        private Statement partitionStatement;
        private Statement orderStatement;

        public Statement getPartitionStatement() {
            return partitionStatement;
        }

        public void setPartitionStatement(Statement partitionStatement) {
            this.partitionStatement = partitionStatement;
            if (partitionStatement != null) {
                partitionStatement.parentStatement = this;
            }
        }

        public Statement getOrderStatement() {
            return orderStatement;
        }

        public void setOrderStatement(Statement orderStatement) {
            this.orderStatement = orderStatement;
            if (orderStatement != null) {
                orderStatement.parentStatement = this;
            }
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(partitionStatement, orderStatement);
        }

        public static class PartitionStatement extends Statement {
            private Statement statement;

            public Statement getStatement() {
                return statement;
            }

            public void setStatement(Statement statement) {
                this.statement = statement;
                if (statement != null) {
                    statement.parentStatement = this;
                }
            }

            @Override
            protected Boolean isNeedInnerCache() {
                return isNeedInnerCache(statement);
            }
        }
    }
}