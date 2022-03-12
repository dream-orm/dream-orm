package com.moxa.dream.module.reflect.factory;


import com.moxa.dream.module.reflect.wrapper.PropertyInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanObjectFactory implements ObjectFactory {

    Object result;

    BeanObjectFactory() {

    }

    public BeanObjectFactory(Class type) {
        result = newInstance(type);
    }

    protected Object newInstance(Class<?> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalCallerException(e);
        }
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        Method writeMethod = propertyInfo.getWriteMethod();
        if (writeMethod != null) {
            try {
                writeMethod.invoke(result, value);
            } catch (Exception e) {
                throw new IllegalCallerException(e);
            }
        } else {
            Field field = propertyInfo.getField();
            ObjectUtil.requireNonNull(field, "Property 'field' is required");
            try {
                field.set(result, value);
            } catch (Exception e) {
                throw new IllegalCallerException(e);
            }
        }
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        return get(result, propertyInfo);
    }

    protected Object get(Object result, PropertyInfo propertyInfo) {
        Method readMethod = propertyInfo.getReadMethod();
        if (readMethod != null) {
            try {
                return readMethod.invoke(result);
            } catch (Exception e) {
                throw new IllegalCallerException(e);
            }
        }
        Field field = propertyInfo.getField();
        ObjectUtil.requireNonNull(field, "Property 'field' is required");
        try {
            return field.get(result);
        } catch (Exception e) {
            throw new IllegalCallerException(e);
        }
    }

    @Override
    public Object getObject() {
        return result;
    }
}