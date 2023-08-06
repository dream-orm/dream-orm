package com.dream.antlr.smt;

public class WhereStatement extends Statement {
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
}
