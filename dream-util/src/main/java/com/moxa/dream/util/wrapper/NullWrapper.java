package com.moxa.dream.util.wrapper;


public class NullWrapper extends ObjectWrapper {

    @Override
    public Object set(PropertyToken propertyToken, Object value) throws WrapperException {
        throw new WrapperException("null对象不支持Set方法");
    }

    @Override
    public Object get(PropertyToken propertyToken) {
        return null;
    }

    @Override
    public Object getObject() {
        return null;
    }
}
