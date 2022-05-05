package com.moxa.dream.system.table;

import java.lang.reflect.Field;

public class ColumnInfo {
    private final String table;
    private final String column;
    private final String name;
    private final Field field;
    private final boolean primary;
    private final int jdbcType;

    public ColumnInfo(String table, String column, Field field, boolean primary, int jdbcType) {
        this.table = table;
        this.column = column;
        this.field = field;
        this.primary = primary;
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
        return primary;
    }
}
