package com.moxa.dream.antlr.smt;

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
        this.column = column;
        if (column != null) {
            column.parentStatement = this;
        }
    }

    public Statement getAlias() {
        return alias;
    }

    public void setAlias(Statement alias) {
        this.alias = alias;
        if (alias != null) {
            alias.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(column, alias);
    }
}
