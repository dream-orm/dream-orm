package com.moxa.dream.antlr.smt;

public class ForUpdateStatement extends Statement {
    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }
}
