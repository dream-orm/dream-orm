package com.moxa.dream.module.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyInfo {
    private String label;
    private Field field;
    private Method writeMethod;

    public String getLabel() {
        return label;
    }

    public Field getField() {
        return field;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }
}
