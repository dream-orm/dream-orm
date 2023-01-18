package com.moxa.dream.antlr.smt;

public class CaseStatement extends Statement {
    private Statement caseColumn;
    private Statement whenthenList;
    private Statement elseColumn;

    public Statement getWhenthenList() {
        return whenthenList;
    }

    public void setWhenthenList(Statement whenthenList) {
        this.whenthenList = whenthenList;
        if (whenthenList != null) {
            whenthenList.parentStatement = this;
        }
    }

    public Statement getCaseColumn() {
        return caseColumn;
    }

    public void setCaseColumn(Statement caseColumn) {
        this.caseColumn = caseColumn;
        if (caseColumn != null) {
            caseColumn.parentStatement = this;
        }
    }


    public Statement getElseColumn() {
        return elseColumn;
    }

    public void setElseColumn(Statement elseColumn) {
        this.elseColumn = elseColumn;
        if (elseColumn != null) {
            elseColumn.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(caseColumn, whenthenList, elseColumn);
    }

    public static class WhenThenStatement extends Statement {
        private Statement when;
        private Statement then;

        public Statement getWhen() {
            return when;
        }

        public void setWhen(Statement when) {
            this.when = when;
            if (when != null) {
                when.parentStatement = this;
            }
        }

        public Statement getThen() {
            return then;
        }

        public void setThen(Statement then) {
            this.then = then;
            if (then != null) {
                then.parentStatement = this;
            }
        }

        @Override
        protected Boolean isNeedInnerCache() {
            return isNeedInnerCache(when, then);
        }
    }


}
