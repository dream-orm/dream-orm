package com.moxa.dream.util.wrapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ObjectWrapper {
    protected static Map<Class, ReflectClass> reflectClassMap = new HashMap<>();
    protected ObjectWrapper tempWrapper;

    public static ObjectWrapper wrapper(Object object) {
        ObjectWrapper wrapper;
        if (object == null)
            wrapper = new NullWrapper();
        else if (object instanceof ObjectWrapper) {
            wrapper = (ObjectWrapper) object;
        } else if (object instanceof Map) {
            wrapper = new MapWrapper((Map) object);
        } else if (object instanceof Collection) {
            wrapper = new CollectionWrapper((Collection) object);
        } else {
            ReflectClass reflectClass = reflectClassMap.get(object.getClass());
            if (reflectClass == null) {
                synchronized (ObjectWrapper.class) {
                    reflectClass = reflectClassMap.get(object.getClass());
                    if (reflectClass == null) {
                        reflectClass = new ReflectClass(object);
                        reflectClassMap.put(object.getClass(), reflectClass);
                    }
                }
            }
            wrapper = new BeanWrapper(reflectClass);
        }
        return wrapper;
    }

    public static void clear() {
        reflectClassMap.clear();
    }

    public void set(String property, Object value) throws WrapperException {
        set(new PropertyToken(property), value);
    }

    public Object get(String property) throws WrapperException {
        if (tempWrapper != null) {
            Object result = tempWrapper.get(new PropertyToken(property));
            if (result == null)
                return get(new PropertyToken(property));
            else
                return result;
        } else
            return get(new PropertyToken(property));
    }

    public abstract Object getObject();

    protected abstract void set(PropertyToken propertyToken, Object value) throws WrapperException;

    protected abstract Object get(PropertyToken propertyToken);

    public void setTemp(Object temp) {
        if (temp == null) {
            tempWrapper = null;
        } else {
            tempWrapper = ObjectWrapper.wrapper(temp);
        }
    }
}
