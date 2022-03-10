package com.moxa.dream.module.producer;

import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyInfo {
    private String label;
    private Field field;
    private Method writeMethod;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setField(Field field) {
        if (field != null) {
            field.trySetAccessible();
            this.field = field;
        }
    }

    public void setWriteMethod(Method writeMethod) {
        if (writeMethod != null) {
            writeMethod.trySetAccessible();
            this.writeMethod = writeMethod;
        }
    }

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
