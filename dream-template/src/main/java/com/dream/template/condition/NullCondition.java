package com.dream.template.condition;

public class NullCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " is null";
    }
}
