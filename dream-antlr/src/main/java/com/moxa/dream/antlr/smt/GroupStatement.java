package com.moxa.dream.antlr.smt;

public class GroupStatement extends Statement {
    private Statement group;

    public Statement getGroup() {
        return group;
    }

    public void setGroup(Statement group) {
        this.group = wrapParent(group);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(group);
    }
}
