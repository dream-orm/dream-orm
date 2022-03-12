package com.moxa.dream.module.reflect.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyInfo {
    private String label;
    private Field field;
    private Method writeMethod;
    private Method readMethod;

    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        if (field != null) {
            field.trySetAccessible();
            this.field = field;
        }
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Method writeMethod) {
        if (writeMethod != null) {
            writeMethod.trySetAccessible();
            this.writeMethod = writeMethod;
        }
    }
}
