package com.moxa.dream.template.condition;

public class NotNullCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " not is null";
    }
}
