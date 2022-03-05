package com.moxa.dream.util.wrapper;

import java.lang.reflect.Type;
import java.util.Map;

public class MapWrapper extends ObjectWrapper {
    private Map object;
    private Type type;

    public MapWrapper(Map map, Type type) {
        this.object = map;
        this.type = type;
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
                resultWrapper = wrapper(type);
                result = resultWrapper.getObject();
                object.put(name, result);
            } else
                resultWrapper = wrapper(result);
            return resultWrapper.set(propertyToken.next(), value);
        } else {
            Object result = object.get(name);
            object.put(name, value);
            return result;
        }
    }

    @Override
    public Object getObject() {
        return object;
    }
}
