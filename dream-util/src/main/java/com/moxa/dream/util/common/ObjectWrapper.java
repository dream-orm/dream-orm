package com.moxa.dream.util.common;

import com.moxa.dream.util.reflection.factory.ObjectFactory;
import com.moxa.dream.util.reflection.wrapper.ObjectFactoryWrapper;

import java.util.HashMap;
import java.util.Map;

public class ObjectWrapper {
    static Map<Class, ObjectFactoryWrapper> factoryWrapperMap = new HashMap<>();
    private final ObjectFactory objectFactory;

    ObjectWrapper(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public static ObjectWrapper wrapper(Object target) {
        return wrapper(target, true);
    }

    public static ObjectWrapper wrapper(Object target, boolean cache) {
        Class<?> type = target.getClass();
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
        String[] propertyList = property.split("\\.");
        if (propertyList.length > 1) {
            ObjectFactory targetFactory = objectFactory;
            for (int i = 0; i < propertyList.length - 1; i++) {
                Object o = targetFactory.get(propertyList[i]);
                targetFactory = ObjectWrapper.wrapper(o).objectFactory;
            }
            targetFactory.set(propertyList[propertyList.length - 1], value);
        } else {
            objectFactory.set(property, value);
        }
    }

    public Object get(String property) {
        String[] propertyList = property.split("\\.");
        if (propertyList.length > 1) {
            ObjectFactory targetFactory = objectFactory;
            for (int i = 0; i < propertyList.length - 1; i++) {
                Object value = targetFactory.get(propertyList[i]);
                ObjectWrapper wrapper = ObjectWrapper.wrapper(value);
                targetFactory = wrapper.objectFactory;
            }
            return targetFactory.get(propertyList[propertyList.length - 1]);
        } else {
            return objectFactory.get(property);
        }
    }

    public Object getObject() {
        return objectFactory.getObject();
    }
}
