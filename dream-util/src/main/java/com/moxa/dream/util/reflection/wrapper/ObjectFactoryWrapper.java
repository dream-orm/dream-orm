package com.moxa.dream.util.reflection.wrapper;

import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.factory.ObjectFactory;
import com.moxa.dream.util.reflection.util.NonCollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public interface ObjectFactoryWrapper {
    static ObjectFactoryWrapper wrapper(Class type) {
        if (ReflectUtil.isBaseClass(type)) {
            return new BasicObjectFactoryWrapper();
        } else if (type.isAssignableFrom(ArrayDeque.class)) {
            return new ArrayDequeObjectFactoryWrapper();
        } else if (type.isAssignableFrom(ArrayList.class)) {
            return new ArrayListObjectFactoryWrapper();
        } else if (type.isAssignableFrom(HashMap.class)) {
            return new HashMapObjectFactoryWrapper();
        } else if (type.isAssignableFrom(HashSet.class)) {
            return new HashSetObjectFactoryWrapper();
        } else if (type.isAssignableFrom(TreeSet.class)) {
            return new TreeSetObjectFactoryWrapper();
        } else if (type.isAssignableFrom(TreeMap.class)) {
            return new TreeMapObjectFactoryWrapper();
        } else if (type.isAssignableFrom(ConcurrentHashMap.class)) {
            return new ConcurrentHashMapObjectFactoryWrapper();
        } else if (type.isAssignableFrom(LinkedList.class)) {
            return new LinkedListObjectFactoryWrapper();
        } else if (type.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSetObjectFactoryWrapper();
        } else if (type.isAssignableFrom(NonCollection.class)) {
            return new NonCollectionObjectFactoryWrapper();
        } else if (Collection.class.isAssignableFrom(type)) {
            return new CollectionObjectFactoryWrapper(type);
        } else if (Map.class.isAssignableFrom(type)) {
            return new MapObjectFactoryWrapper(type);
        } else if (type.isArray()) {
            return new ArrayObjectFactoryWrapper();
        } else {
            return new BeanObjectFactoryWrapper(type);
        }
    }

    default ObjectFactory newObjectFactory() {
        return newObjectFactory(null);
    }

    ObjectFactory newObjectFactory(Object target);

}
