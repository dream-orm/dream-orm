package com.dream.antlr.smt;

public class HavingStatement extends Statement {
    private Statement condition;

    public Statement getCondition() {
        return condition;
    }

    public void setCondition(Statement condition) {
        this.condition = wrapParent(condition);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(condition);
    }

    @Override
    public HavingStatement clone() {
        HavingStatement havingStatement = (HavingStatement) super.clone();
        havingStatement.condition = clone(condition);
        return havingStatement;
    }
}
