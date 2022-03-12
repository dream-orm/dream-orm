package com.moxa.dream.util.wrapper;

import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectClass {
    private static Map<Class, Map<String, Field>> cacheFieldMap = new HashMap<>();
    private Object target;
    private Map<String, Field> fieldMap;

    private ReflectClass(Object target, Map<String, Field> fieldMap) {
        this.target = target;
        this.fieldMap = fieldMap;
    }

    public static ReflectClass newInstance(Object target) {
        Class<?> type = target.getClass();
        Map<String, Field> fieldMap = cacheFieldMap.get(type);
        if (fieldMap == null) {
            synchronized (cacheFieldMap) {
                fieldMap = cacheFieldMap.get(type);
                if (fieldMap == null) {
                    fieldMap = new HashMap<>();
                    for (Field field : ReflectUtil.findField(type)) {
                        field.trySetAccessible();
                        fieldMap.put(field.getName(), field);
                    }
                    cacheFieldMap.put(type, fieldMap);
                }
            }
        }
        return new ReflectClass(target, fieldMap);
    }


    public Object set(String name, Object value) throws WrapperException {
        Field field = fieldMap.get(name);
        if (field == null)
            throw new WrapperException("'" + target.getClass().getName() + "'字段属性'" + name + "'不存在");
        try {
            Object result = field.get(target);
            field.set(target, value);
            return result;
        } catch (IllegalAccessException e) {
            throw new WrapperException("'" + target.getClass().getName() + "'字段属性'" + name + "'设置失败", e);
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
            throw new WrapperException("'" + target.getClass().getName() + "'字段属性'" + name + "'不存在");
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new WrapperException("'" + target.getClass().getName() + "'获取字段值'" + name + "'失败", e);
        }
    }
}
