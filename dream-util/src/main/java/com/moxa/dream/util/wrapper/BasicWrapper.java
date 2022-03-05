package com.moxa.dream.util.wrapper;

import com.moxa.dream.util.reflect.ReflectUtil;

public class BasicWrapper extends ObjectWrapper {
    private Class type;
    private Object value;

    public BasicWrapper(Class type) {
        this.type = ReflectUtil.castClass(type);
    }

    @Override
    public Object getObject() {
        return value;
    }

    @Override
    protected Object set(PropertyToken propertyToken, Object value) throws WrapperException {
        Object oldValue = this.value;
        if (value != null && !type.isAssignableFrom(value.getClass()))
            throw new WrapperException("'" + type.getName() + "'与'" + value.getClass().getName() + "'类型不匹配");
        this.value = value;
        return oldValue;
    }

    @Override
    protected Object get(PropertyToken propertyToken) {
        return value;
    }
}
