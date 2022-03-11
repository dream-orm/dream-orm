package com.moxa.dream.module.producer.wrapper;

import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.util.NonCollection;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.*;

public interface ObjectFactoryWrapper {
    static ObjectFactoryWrapper wrapper(Class type) {
        if (type.isAssignableFrom(ArrayDeque.class)) {
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
        } else if (type.isAssignableFrom(LinkedList.class)) {
            return new LinkedListObjectFactoryWrapper();
        } else if (type.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSetObjectFactoryWrapper();
        } else if (type.isAssignableFrom(NonCollection.class)) {
            return new NonCollectionObjectFactoryWrapper();
        } else if (Collection.class.isAssignableFrom(type)) {
            return new CollectionObjectFactoryWrapper(type);
        } else if (ReflectUtil.isBaseClass(type)) {
            return new BaseObjectFactoryWrapper();
        } else {
            return new BeanObjectFactoryWrapper(type);
        }
    }

    ObjectFactory newObjectFactory();
}
