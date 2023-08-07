package com.dream.system.table;

import com.dream.system.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class ColumnInfo {
    private String table;
    private String column;
    private String name;
    private Field field;
    private Map<Class<? extends Annotation>, Annotation> annotationMap;
    private int jdbcType;

    public ColumnInfo(String table, String column, Field field, Map<Class<? extends Annotation>, Annotation> annotationMap, int jdbcType) {
        this.table = table;
        this.column = column;
        this.field = field;
        this.annotationMap = annotationMap;
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

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return (T) annotationMap.get(annotationClass);
    }

    public boolean isPrimary() {
        return getAnnotation(Id.class) != null;
    }
}
