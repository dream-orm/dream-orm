package com.dream.antlr.smt;

public class WithStatement extends Statement {
    private Statement aliasStatement;
    private Statement statement;

    public Statement getAliasStatement() {
        return aliasStatement;
    }

    public void setAliasStatement(Statement aliasStatement) {
        this.aliasStatement = wrapParent(aliasStatement);
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = wrapParent(statement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(aliasStatement, statement);
    }

    @Override
    public WithStatement clone() {
        WithStatement withStatement = (WithStatement) super.clone();
        withStatement.setAliasStatement(clone(aliasStatement));
        withStatement.setStatement(clone(statement));
        return withStatement;
    }

    public static class WithAliasStatement extends Statement {
        private Statement aliasStatement;
        private Statement statement;

        public Statement getAliasStatement() {
            return aliasStatement;
        }

        public void setAliasStatement(Statement aliasStatement) {
            this.aliasStatement = wrapParent(aliasStatement);
        }

        public Statement getStatement() {
            return statement;
        }

        public void setStatement(Statement statement) {
            this.statement = wrapParent(statement);
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(aliasStatement, statement);
        }

        @Override
        public WithAliasStatement clone() {
            WithAliasStatement withAliasStatement = (WithAliasStatement) super.clone();
            withAliasStatement.setAliasStatement(clone(aliasStatement));
            withAliasStatement.setStatement(clone(statement));
            return withAliasStatement;
        }

    }
}
