package com.moxa.dream.util.reflection.factory;

import com.moxa.dream.util.common.ObjectUtil;

public class BasicObjectFactory implements ObjectFactory {
    private Object result;

    public BasicObjectFactory(Object target) {
        result = target;
    }

    @Override
    public void set(String property, Object value) {
        this.result = value;
    }

    @Override
    public Object get(String property) {
        if (ObjectUtil.isNull(property)) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Object getObject() {
        return result;
    }
}
