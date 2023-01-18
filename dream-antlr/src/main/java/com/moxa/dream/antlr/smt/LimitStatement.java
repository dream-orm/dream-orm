package com.moxa.dream.antlr.smt;

public class LimitStatement extends Statement {
    private Statement first;
    private Statement second;
    private boolean offset;

    public Statement getFirst() {
        return first;
    }

    public void setFirst(Statement first) {
        this.first = first;
        if (first != null) {
            first.parentStatement = this;
        }
    }

    public Statement getSecond() {
        return second;
    }

    public void setSecond(Statement second) {
        this.second = second;
        if (second != null) {
            second.parentStatement = this;
        }
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
}
