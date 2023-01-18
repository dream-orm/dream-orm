package com.moxa.dream.antlr.smt;

import com.moxa.dream.antlr.exception.AntlrException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

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
                            break;
                        }
                    } else if (child instanceof List) {
                        int i;
                        for (i = 0; i < ((List) child).size(); i++) {
                            Object value = ((List) child).get(i);
                            if (value != null && value == this) {
                                ((List) child).set(i, statement);
                                break;
                            }
                        }
                        if (i < ((List) child).size()) {
                            break;
                        }
                    } else if (child.getClass().isArray()) {
                        int length = Array.getLength(child);
                        int i;
                        for (i = 0; i < length; i++) {
                            Object value = Array.get(child, i);
                            if (value != null && value == this) {
                                Array.set(child, i, statement);
                                break;
                            }
                        }
                        if (i < length) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                throw new AntlrException("替换" + this.getClass().getName() + "为" + statement.getClass().getName() + "失败", e);
            }
        }
        if (index < fieldList.length) {
            Statement parentStatement
                    = statement.parentStatement
                    = this.parentStatement;
            this.parentStatement = null;
            while (parentStatement != null) {
                parentStatement.needCache = null;
                parentStatement = parentStatement.getParentStatement();
            }
        } else {
            throw new AntlrException("不能替换" + this.getClass().getName() + "为" + statement.getClass().getName());
        }
    }

}
