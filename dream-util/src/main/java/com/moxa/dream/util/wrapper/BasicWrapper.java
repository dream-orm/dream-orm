package com.moxa.dream.util.wrapper;

import java.util.HashMap;
import java.util.Map;

public class BasicWrapper extends ObjectWrapper {
    private Object object;

    public BasicWrapper(Object object) {
        this.object = object;
    }

    @Override
    public Object get(PropertyToken propertyToken) {
        return object;
    }

    @Override
    public void set(PropertyToken propertyToken, Object value) throws WrapperException {
        this.object=value;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
