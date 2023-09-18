package com.dream.template.util;

import com.dream.template.condition.Condition;

public class ConditionObject {
    private String table;
    private String column;
    private String field;
    private boolean filterNull;
    private boolean or;
    private Condition condition;

    public ConditionObject(String table, String column,String field, boolean filterNull, boolean or, Condition condition) {
        this.table = table;
        this.column = column;
        this.field = field;
        this.filterNull = filterNull;
        this.or = or;
        this.condition = condition;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getField() {
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
