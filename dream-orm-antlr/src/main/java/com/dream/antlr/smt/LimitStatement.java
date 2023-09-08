package com.dream.antlr.smt;

public class LimitStatement extends Statement {
    private Statement first;
    private Statement second;
    private boolean offset;

    public Statement getFirst() {
        return first;
    }

    public void setFirst(Statement first) {
        this.first = wrapParent(first);
    }

    public Statement getSecond() {
        return second;
    }

    public void setSecond(Statement second) {
        this.second = wrapParent(second);
    }

    public boolean isOffset() {
        return offset;
    }

    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(first, second);
    }

    @Override
    public LimitStatement clone() {
        LimitStatement limitStatement = (LimitStatement) super.clone();
        limitStatement.setFirst(clone(first));
        limitStatement.setSecond(clone(second));
        limitStatement.setOffset(offset);
        return limitStatement;
    }
}
