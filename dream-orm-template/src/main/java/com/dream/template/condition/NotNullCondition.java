package com.dream.template.condition;

public class NotNullCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " is not null";
    }
}
