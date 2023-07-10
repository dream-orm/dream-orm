package com.moxa.dream.antlr.smt;

public class PreSelectStatement extends Statement {
    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }
}
