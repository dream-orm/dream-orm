package com.moxa.dream.template.condition;

public class NotNullCondition implements Condition {
    private final String param = "param";

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " not is null";
    }
}
