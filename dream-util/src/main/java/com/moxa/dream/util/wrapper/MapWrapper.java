package com.moxa.dream.util.wrapper;

import java.util.HashMap;
import java.util.Map;

public class MapWrapper extends ObjectWrapper {
    private Map object;
    private ObjectWrapper targetWrapper;

    public MapWrapper(Map map) {
        this.object = map;
        Object target = map.get(null);
        if (target != null) {
            targetWrapper = ObjectWrapper.wrapper(target);
        }
    }

    @Override
    public Object get(PropertyToken propertyToken) {
        String name = propertyToken.getName();
        Object value = object.get(name);
        if (value == null && targetWrapper != null) {
            return targetWrapper.get(propertyToken);
        } else if (propertyToken.hasNext()) {
            return wrapper(value).get(propertyToken.next());
        } else
            return value;
    }

    @Override
    public void set(PropertyToken propertyToken, Object value) throws WrapperException {
        String name = propertyToken.getName();
        if (propertyToken.hasNext()) {
            ObjectWrapper resultWrapper;
            Object result = object.get(name);
            if (result == null) {
                result = new HashMap<>();
                resultWrapper = wrapper(result);
                object.put(name, result);
            } else
                resultWrapper = wrapper(result);
            resultWrapper.set(propertyToken.next(), value);
        } else {
            object.put(name, value);
        }
    }

    @Override
    public Object getObject() {
        return object;
    }
}
