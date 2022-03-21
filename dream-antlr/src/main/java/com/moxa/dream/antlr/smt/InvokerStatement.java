package com.moxa.dream.antlr.smt;

import com.moxa.dream.util.reflect.ReflectException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

public class InvokerStatement extends Statement {
    private String namespace;
    private String function;
    private Statement paramStatement;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Statement getParamStatement() {
        return paramStatement;
    }

    public void setParamStatement(Statement paramStatement) {
        this.paramStatement = paramStatement;
        if (paramStatement != null)
            paramStatement.parentStatement = this;
    }

    public void setStatement(Statement statement) {
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
                throw new ReflectException(e);
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
            throw new ReflectException("can not set statement");
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return false;
    }


}
