package com.moxa.dream.util.wrapper;


import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public abstract class ObjectWrapper {

    public static ObjectWrapper wrapper(Object object) {
        return wrapper(object, null);
    }

    public static ObjectWrapper wrapper(Object object, Type type) {
        ObjectWrapper wrapper;
        if (object == null)
            wrapper = new NullWrapper();
        else if (object instanceof ObjectWrapper) {
            wrapper = (ObjectWrapper) object;
        } else if (object instanceof Map) {
            wrapper = new MapWrapper((Map) object, type);
        } else if (object instanceof Collection) {
            wrapper = new CollectionWrapper((Collection) object, type);
        } else
            wrapper = new BeanWrapper(new ReflectClass(object));
        return wrapper;
    }

    public static ObjectWrapper wrapper(Type type) throws WrapperException {
        if (type instanceof Class) {
            Object object;
            if (ReflectUtil.isBaseClass((Class) type) || Object.class == type) {
                return new BasicWrapper((Class) type);
            } else if (((Class<?>) type).isArray()) {
                object = Array.newInstance(((Class<?>) type).getComponentType(), 0);
            } else
                object = ReflectUtil.create((Class<?>) type);
            return wrapper(object, null);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type genericType = actualTypeArguments[actualTypeArguments.length - 1];
            return wrapper(ReflectUtil.create((Class<?>) ((ParameterizedType) type).getRawType()), genericType);
        } else
            throw new WrapperException("不支持" + type.getTypeName());
    }

    public Object set(String property, Object value) throws WrapperException {
        return set(new PropertyToken(property), value);
    }

    public Object get(String property) throws WrapperException {
        return get(new PropertyToken(property));
    }

    public abstract Object getObject();

    protected abstract Object set(PropertyToken propertyToken, Object value) throws WrapperException;

    protected abstract Object get(PropertyToken propertyToken);
}
