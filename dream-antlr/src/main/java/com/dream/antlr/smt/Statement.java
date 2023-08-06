package com.dream.antlr.smt;

import com.dream.antlr.exception.AntlrException;

import java.lang.reflect.Field;

/**
 * SQL语句对应的抽象树顶级类
 */
public abstract class Statement {

    protected Statement parentStatement;
    Boolean needCache;
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

    public Statement getParentStatement() {
        return parentStatement;
    }

    public void setParentStatement(Statement parentStatement) {
        this.parentStatement = parentStatement;
    }

    protected <T extends Statement> T wrapParent(T statement) {
        if (statement != null) {
            statement.parentStatement = this;
        }
        return statement;
    }

    protected boolean isNeedInnerCache(Statement... statements) {
        needCache = true;
        for (Statement statement : statements) {
            if (statement != null) {
                needCache &= statement.isNeedCache();
                if (!needCache) {
                    return false;
                }
            }
        }
        return needCache = true;
    }

    public void replaceWith(Statement statement) throws AntlrException {
        Field[] fieldList = parentStatement.getClass().getDeclaredFields();
        int index;
        for (index = 0; index < fieldList.length; index++) {
            Field field = fieldList[index];
            field.setAccessible(true);
            Object child;
            try {
                child = field.get(parentStatement);
                if (child != null) {
                    if (child instanceof Statement) {
                        if (child == this) {
                            field.set(parentStatement, statement);
                            Statement parentStatement
                                    = statement.parentStatement
                                    = this.parentStatement;
                            while (parentStatement != null) {
                                parentStatement.needCache = null;
                                parentStatement = parentStatement.getParentStatement();
                            }
                            this.parentStatement = null;
                            return;
                        }
                    } else if (child instanceof Statement[]) {
                        Statement[] columnList = (Statement[]) child;
                        int i;
                        for (i = 0; i < columnList.length; i++) {
                            Statement value = columnList[i];
                            if (value != null && value == this) {
                                columnList[i] = statement;
                                Statement parentStatement
                                        = statement.parentStatement
                                        = this.parentStatement;
                                while (parentStatement != null) {
                                    parentStatement.needCache = null;
                                    parentStatement = parentStatement.getParentStatement();
                                }
                                this.parentStatement = null;
                                return;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new AntlrException("替换" + this.getClass().getName() + "为" + statement.getClass().getName() + "失败", e);
            }
        }
        throw new AntlrException("不能替换" + this.getClass().getName() + "为" + statement.getClass().getName());
    }

}
