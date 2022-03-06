package com.moxa.dream.antlr.smt;

public abstract class Statement {

    protected Statement parentStatement;
    private Boolean needCache;
    private String quickValue;

    protected abstract Boolean isNeedInnerCache();

    public int getNameId() {
        return this.getClass().getSimpleName().hashCode();
    }

    public String getQuickValue() {
        return quickValue;
    }

    public void setQuickValue(String value) {
        this.quickValue = value;
    }

    public Boolean isNeedCache() {
        if (needCache != null)
            return needCache;
        return isNeedInnerCache();
    }

    public Statement getParentStatement() {
        return parentStatement;
    }

    public void setParentStatement(Statement parentStatement) {
        this.parentStatement = parentStatement;
    }

    protected boolean isNeedInnerCache(Statement... statements) {
        needCache = true;
        for (Statement statement : statements) {
            if (statement != null) {
                needCache &= statement.isNeedCache();
                if (!needCache)
                    return false;
            }
        }
        return needCache = true;
    }

}
