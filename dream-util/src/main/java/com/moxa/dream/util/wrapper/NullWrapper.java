package com.moxa.dream.util.wrapper;

public class NullWrapper extends ObjectWrapper {

    @Override
    public void set(PropertyToken propertyToken, Object value) throws WrapperException {

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
