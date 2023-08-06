package com.dream.antlr.smt;

public class AliasStatement extends Statement {
    private boolean showAlias;
    private Statement column;
    private Statement alias;

    public boolean isShowAlias() {
        return showAlias;
    }

    public void setShowAlias(boolean showAlias) {
        this.showAlias = showAlias;
    }

    public Statement getColumn() {
        return column;
    }

    public void setColumn(Statement column) {
        this.column = wrapParent(column);
    }

    public Statement getAlias() {
        return alias;
    }

    public void setAlias(Statement alias) {
        this.alias = wrapParent(alias);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(column, alias);
    }
}
