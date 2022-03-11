package com.moxa.dream.util.wrapper;


import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;

public class BeanWrapper extends ObjectWrapper {
    private ReflectClass object;

    public BeanWrapper(ReflectClass object) {
        this.object = object;
    }

    @Override
    public Object get(PropertyToken propertyToken) {
        String name = propertyToken.getName();
        Object value = object.get(name);
        if (propertyToken.hasNext()) {
            return wrapper(value).get(propertyToken.next());
        } else
            return value;
    }

    @Override
    public Object set(PropertyToken propertyToken, Object value) throws WrapperException {
        String name = propertyToken.getName();
        if (propertyToken.hasNext()) {
            ObjectWrapper resultWrapper;
            Object result = object.get(name);
            if (result == null) {
                Field field = object.getField(name);
                result = ReflectUtil.create(field.getType());
                object.set(name, result);
            }
            resultWrapper = wrapper(result);
            return resultWrapper.set(propertyToken.next(), value);
        } else
            return object.set(name, value);
    }

    @Override
    public Object getObject() {
        return object.getTarget();
    }
}