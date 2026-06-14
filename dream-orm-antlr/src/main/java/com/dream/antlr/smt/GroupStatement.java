package com.dream.antlr.smt;

public class GroupStatement extends Statement {
    private Statement group;

    public Statement getGroup() {
        return group;
    }

    public void setGroup(Statement group) {
        this.group = group;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(group);
    }

    @Override
    public GroupStatement clone() {
        GroupStatement groupStatement = (GroupStatement) super.clone();
        groupStatement.setGroup(clone(group));
        return groupStatement;
    }
}
