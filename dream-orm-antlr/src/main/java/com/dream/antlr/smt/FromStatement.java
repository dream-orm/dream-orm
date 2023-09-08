package com.dream.antlr.smt;

public class FromStatement extends Statement {
    private Statement mainTable;
    private Statement joinList;

    public Statement getMainTable() {
        return mainTable;
    }

    public void setMainTable(Statement mainTable) {
        this.mainTable = wrapParent(mainTable);
    }

    public Statement getJoinList() {
        return joinList;
    }

    public void setJoinList(Statement joinList) {
        this.joinList = wrapParent(joinList);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(mainTable, joinList);
    }

    @Override
    public FromStatement clone() {
        FromStatement fromStatement = (FromStatement) super.clone();
        fromStatement.setMainTable(clone(mainTable));
        fromStatement.setJoinList(clone(joinList));
        return fromStatement;
    }
}
