package com.dream.system.table;

import com.dream.system.annotation.Id;

import java.lang.reflect.Field;

public class ColumnInfo {
    private String table;
    private String column;
    private String name;
    private Field field;
    private int jdbcType;

    public ColumnInfo(String table, String column, Field field, int jdbcType) {
        this.table = table;
        this.column = column;
        this.field = field;
        this.jdbcType = jdbcType;
        this.name = field.getName();

    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public boolean isPrimary() {
        return field.getAnnotation(Id.class) != null;
    }
}
