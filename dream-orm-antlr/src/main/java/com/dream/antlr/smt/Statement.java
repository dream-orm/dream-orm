package com.dream.antlr.smt;

import java.io.Serializable;

/**
 * SQL语句对应的抽象树顶级类
 */
public abstract class Statement implements Serializable, Cloneable {

    protected Statement actualStatement;
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
        if (needCache != null) {
            return needCache;
        }
        return isNeedInnerCache();
    }

    public Statement getActualStatement() {
        return actualStatement;
    }

    public void setActualStatement(Statement actualStatement) {
        this.actualStatement = actualStatement;
    }

    protected final boolean isNeedInnerCache(Statement... statements) {
        needCache = true;
        for (Statement statement : statements) {
            if (statement != null) {
                needCache = statement.isNeedCache();
                if (!needCache) {
                    return false;
                }
            }
        }
        return needCache = true;
    }

    @Override
    public Statement clone() {
        try {
            Statement statement = (Statement) super.clone();
            statement.actualStatement = clone(actualStatement);
            statement.needCache = needCache;
            statement.quickValue = quickValue;
            return statement;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    protected final Statement clone(Statement statement) {
        if (statement == null) {
            return null;
        } else {
            return statement.clone();
        }
    }
}
