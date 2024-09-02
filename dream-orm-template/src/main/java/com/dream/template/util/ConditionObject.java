package com.dream.template.util;

import com.dream.template.condition.Condition;

import java.lang.reflect.Field;

public class ConditionObject {
    private String column;
    private Field field;
    private boolean filterNull;
    private boolean or;
    private Condition condition;

    public ConditionObject(String column, Field field, boolean filterNull, boolean or, Condition condition) {
        this.column = column;
        this.field = field;
        this.filterNull = filterNull;
        this.or = or;
        this.condition = condition;
    }

    public String getColumn() {
        return column;
    }

    public Field getField() {
        return field;
    }

    public boolean isFilterNull() {
        return filterNull;
    }

    public boolean isOr() {
        return or;
    }

    public Condition getCondition() {
        return condition;
    }
}
