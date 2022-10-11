package com.moxa.dream.template.condition;

public class EndWithCondition implements Condition {
    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + field + "=";
    }
}
