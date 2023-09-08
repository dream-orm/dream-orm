package com.dream.antlr.smt;

public class SelectStatement extends Statement {
    private boolean distinct;
    private ListColumnStatement selectList;

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public ListColumnStatement getSelectList() {
        return selectList;
    }

    public void setSelectList(ListColumnStatement selectList) {
        this.selectList = wrapParent(selectList);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(selectList);
    }

    @Override
    public SelectStatement clone() {
        SelectStatement selectStatement = (SelectStatement) super.clone();
        selectStatement.setDistinct(distinct);
        selectStatement.setSelectList((ListColumnStatement) clone(selectList));
        return selectStatement;
    }
}
