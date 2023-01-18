package com.moxa.dream.antlr.smt;

public class SelectStatement extends Statement {
    private PreSelectStatement preSelect;
    private ListColumnStatement selectList;

    public PreSelectStatement getPreSelect() {
        return preSelect;
    }

    public void setPreSelect(PreSelectStatement preSelect) {
        this.preSelect = preSelect;
        if (preSelect != null) {
            preSelect.parentStatement = this;
        }
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
        return isNeedInnerCache(preSelect, selectList);
    }
}
