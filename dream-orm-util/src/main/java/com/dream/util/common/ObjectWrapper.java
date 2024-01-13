package com.dream.util.common;

import com.dream.util.reflection.factory.ObjectFactory;
import com.dream.util.reflection.wrapper.ObjectFactoryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ObjectWrapper {
    static Map<Class, ObjectFactoryWrapper> factoryWrapperMap = new HashMap<>(4);
    private final ObjectFactory objectFactory;

    ObjectWrapper(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public static ObjectWrapper wrapper(Object target) {
        return wrapper(target, true);
    }

    public static ObjectWrapper wrapper(Object target, boolean cache) {
        return wrapper(target.getClass(), target, cache);
    }

    public static ObjectWrapper wrapper(Class type) {
        return wrapper(type, true);
    }

    public static ObjectWrapper wrapper(Class type, boolean cache) {
        return wrapper(type, null, cache);
    }

    private static ObjectWrapper wrapper(Class type, Object target, boolean cache) {
        ObjectFactoryWrapper factoryWrapper = factoryWrapperMap.get(type);
        if (factoryWrapper == null) {
            synchronized (factoryWrapperMap) {
                factoryWrapper = factoryWrapperMap.get(type);
                if (factoryWrapper == null) {
                    factoryWrapper = ObjectFactoryWrapper.wrapper(type);
                    if (cache) {
                        factoryWrapperMap.put(type, factoryWrapper);
                    }
                }
            }
        }
        return new ObjectWrapper(factoryWrapper.newObjectFactory(target));
    }

    public static ObjectFactoryWrapper remove(Class<?> type) {
        return factoryWrapperMap.remove(type);
    }

    public static void clear() {
        factoryWrapperMap.clear();
    }

    public void set(String property, Object value) {
        if (!ObjectUtil.isNull(property) && property.indexOf(".") >= 0) {
            StringTokenizer tokenizer = new StringTokenizer(property, ".");
            ObjectFactory targetFactory = objectFactory;
            String token = tokenizer.nextToken();
            while (tokenizer.hasMoreTokens()) {
                Object targetObject = targetFactory.get(token);
                ObjectWrapper wrapper = ObjectWrapper.wrapper(targetObject);
                targetFactory = wrapper.objectFactory;
                token = tokenizer.nextToken();
            }
            targetFactory.set(token, value);
        } else {
            objectFactory.set(property, value);
        }
    }

    public Object get(String property) {
        if (!ObjectUtil.isNull(property) && property.indexOf(".") >= 0) {
            StringTokenizer tokenizer = new StringTokenizer(property, ".");
            ObjectFactory targetFactory = objectFactory;
            String token = tokenizer.nextToken();
            Object value = targetFactory.get(token);
            while (tokenizer.hasMoreTokens()) {
                ObjectWrapper wrapper = ObjectWrapper.wrapper(value);
                targetFactory = wrapper.objectFactory;
                token = tokenizer.nextToken();
                value = targetFactory.get(token);
            }
            return value;
        }
        return objectFactory.get(property);
    }

    public Object getObject() {
        return objectFactory.getObject();
    }
}
