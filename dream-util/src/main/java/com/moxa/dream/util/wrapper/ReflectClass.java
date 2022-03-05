package com.moxa.dream.util.wrapper;

import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectClass {
    private final Class<?> type;
    private Object target;
    private Map<String, Field> fieldMap = new HashMap<>();

    public ReflectClass(Object target) {
        this.target = target;
        this.type = target.getClass();
        for (Field field : ReflectUtil.findField(type)) {
            fieldMap.put(field.getName(), field);
        }
    }

    public Class<?> getType() {
        return type;
    }

    public Object set(String name, Object value) throws WrapperException {
        Field field = fieldMap.get(name);
        if (field == null)
            throw new WrapperException("'" + type.getName() + "'字段属性'" + name + "'不存在");
        try {
            field.setAccessible(true);
            Object result = field.get(target);
            field.set(target, value);
            return result;
        } catch (IllegalAccessException e) {
            throw new WrapperException("'" + type.getName() + "'字段属性'" + name + "'设置失败", e);
        }
    }


    public Object getTarget() {
        return target;
    }

    public void setTarget(Object value) {
        this.target = value;
    }

    public Field getField(String name) {
        return fieldMap.get(name);
    }

    public Object get(String name) throws WrapperException {
        Field field = getField(name);
        if (field == null)
            throw new WrapperException("'" + type.getName() + "'字段属性'" + name + "'不存在");
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new WrapperException("'" + type.getName() + "'获取字段值'" + name + "'失败", e);
        }
    }
}
