package com.dream.antlr.smt;

public class CaseStatement extends Statement {
    private Statement caseColumn;
    private Statement whenThenList;
    private Statement elseColumn;

    public Statement getWhenThenList() {
        return whenThenList;
    }

    public void setWhenThenList(Statement whenThenList) {
        this.whenThenList = whenThenList;
    }

    public Statement getCaseColumn() {
        return caseColumn;
    }

    public void setCaseColumn(Statement caseColumn) {
        this.caseColumn = caseColumn;
    }


    public Statement getElseColumn() {
        return elseColumn;
    }

    public void setElseColumn(Statement elseColumn) {
        this.elseColumn = elseColumn;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(caseColumn, whenThenList, elseColumn);
    }

    @Override
    public Statement clone() {
        CaseStatement caseStatement = (CaseStatement) super.clone();
        caseStatement.setCaseColumn(clone(caseColumn));
        caseStatement.setWhenThenList(clone(whenThenList));
        caseStatement.setElseColumn(clone(elseColumn));
        return caseStatement;
    }

    public static class WhenThenStatement extends Statement {
        private Statement when;
        private Statement then;

        public Statement getWhen() {
            return when;
        }

        public void setWhen(Statement when) {
            this.when = when;
        }

        public Statement getThen() {
            return then;
        }

        public void setThen(Statement then) {
            this.then = then;
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(when, then);
        }

        @Override
        public WhenThenStatement clone() {
            WhenThenStatement whenThenStatement = (WhenThenStatement) super.clone();
            whenThenStatement.setWhen(clone(when));
            whenThenStatement.setThen(clone(then));
            return whenThenStatement;
        }
    }


}
