package com.moxa.dream.template.util;

import com.moxa.dream.template.condition.Condition;

public class ConditionObject {
    private String table;
    private String property;
    private boolean filterNull;
    private boolean or;
    private Condition condition;

    public ConditionObject(String table, String property, boolean filterNull,boolean or, Condition condition) {
        this.table = table;
        this.property = property;
        this.filterNull = filterNull;
        this.or=or;
        this.condition = condition;
    }

    public String getTable() {
        return table;
    }

    public String getProperty() {
        return property;
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
