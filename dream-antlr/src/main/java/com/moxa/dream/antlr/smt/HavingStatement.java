package com.moxa.dream.antlr.smt;

public class HavingStatement extends Statement {
    private Statement condition;

    public Statement getCondition() {
        return condition;
    }

    public void setCondition(Statement condition) {
        this.condition = condition;
        if (condition != null) {
            condition.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(condition);
    }
}
