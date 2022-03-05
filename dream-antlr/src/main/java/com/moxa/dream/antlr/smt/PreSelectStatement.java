package com.moxa.dream.antlr.smt;

public class PreSelectStatement extends Statement {
    private boolean distinct;

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }


    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }
}
