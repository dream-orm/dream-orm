package com.moxa.dream.module.reflect.factory;


import com.moxa.dream.module.reflect.wrapper.PropertyInfo;

public class BaseObjectFactory implements ObjectFactory {
    private Object result;

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        this.result = value;
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        return null;
    }

    @Override
    public Object getObject() {
        return result;
    }
}
