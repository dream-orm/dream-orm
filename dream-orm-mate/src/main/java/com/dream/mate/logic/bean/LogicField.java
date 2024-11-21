package com.dream.mate.logic.bean;

public class LogicField {

    /**
     * 数据库字段名
     */
    private final String column;

    /**
     * 字段值，column=value带入SQL
     */
    private final String value;

    public LogicField(String column, String value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }
}
