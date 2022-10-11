package com.moxa.dream.template.util;

import com.moxa.dream.template.condition.Condition;

public class ConditionObject {
    private String table;
    private String property;
    private boolean filterNull;
    private Condition condition;

    public ConditionObject(String table, String property, boolean filterNull, Condition condition) {
        this.table = table;
        this.property = property;
        this.filterNull = filterNull;
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

    public Condition getCondition() {
        return condition;
    }
}
