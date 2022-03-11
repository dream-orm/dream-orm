package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.ProducerException;
import com.moxa.dream.module.producer.PropertyInfo;
import com.moxa.dream.module.producer.util.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class CollectionObjectFactory implements ObjectFactory {
    Collection result;

    CollectionObjectFactory() {

    }

    public CollectionObjectFactory(Class<? extends Collection> type) {
        if (type.isAssignableFrom(ArrayList.class)) {
            result = new ArrayList();
        } else if (NonCollection.class.isAssignableFrom(type)) {
            result = new NonCollection();
        } else if (type.isAssignableFrom(HashSet.class)) {
            result = new HashSet<>();
        } else if (type.isAssignableFrom(LinkedList.class)) {
            result = new LinkedList();
        } else {
            try {
                result = type.getConstructor().newInstance();
            } catch (Exception e) {
                throw new ProducerException(e);
            }
        }
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        result.add(value);
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        Method readMethod = propertyInfo.getReadMethod();
        if (readMethod != null) {
            try {
                return readMethod.invoke(result);
            } catch (Exception e) {
                return new ProducerException(e);
            }
        }
        Field field = propertyInfo.getField();
        ObjectUtil.requireNonNull(field, "Property 'field' is required");
        try {
            return field.get(result);
        } catch (Exception e) {
            throw new ProducerException(e);
        }
    }

    @Override
    public Object getObject() {
        return result instanceof NonCollection?((NonCollection<?>) result).toObject():result;
    }
}
