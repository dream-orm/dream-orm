package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.ProducerException;
import com.moxa.dream.module.producer.PropertyInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanObjectFactory implements ObjectFactory {

    Object result;

    BeanObjectFactory() {

    }

    public BeanObjectFactory(Class type) {
        try {
            result = type.getConstructor().newInstance();
        } catch (Exception e) {
            throw new ProducerException(e);
        }
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        Method writeMethod = propertyInfo.getWriteMethod();
        if (writeMethod != null) {
            try {
                writeMethod.invoke(result, value);
            } catch (Exception e) {
                throw new ProducerException(e);
            }
        }
        Field field = propertyInfo.getField();
        ObjectUtil.requireNonNull(field, "Property 'field' is required");
        try {
            field.set(result, value);
        } catch (Exception e) {
            throw new ProducerException(e);
        }
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
        return result;
    }
}
