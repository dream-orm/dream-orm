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
        this.selectList = selectList;
        if (selectList != null) {
            selectList.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(selectList);
    }

    @Override
    public SelectStatement clone() {
        SelectStatement selectStatement = (SelectStatement) super.clone();
        selectStatement.distinct = distinct;
        selectStatement.selectList = (ListColumnStatement) clone(selectList);
        return selectStatement;
    }
}
