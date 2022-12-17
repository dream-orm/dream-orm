package com.moxa.dream.util.reflection.factory;

import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflection.wrapper.BeanObjectFactoryWrapper;

import java.util.Collection;
import java.util.List;

public class CollectionObjectFactory extends BeanObjectFactory {
    public CollectionObjectFactory(Collection target) {
        this(target, null);
    }

    public CollectionObjectFactory(Collection target, BeanObjectFactoryWrapper factoryWrapper) {
        super(target, factoryWrapper);
    }

    @Override
    public void set(String property, Object value) {
        if (property == null)
            ((Collection) result).add(value);
        else {
            super.set(property, value);
        }
    }

    @Override
    protected Object get(Object result, String property) {
        if (property == null) {
            return result;
        } else if (Character.isDigit(property.charAt(0))) {
            if (!(result instanceof List)) {
                throw new DreamRunTimeException(result.getClass().getName() + "不支持根据索引获取值");
            }
            List<?> resultList = (List) result;
            int index = Integer.valueOf(property);
            if (index < resultList.size()) {
                return resultList.get(index);
            } else {
                return null;
            }
        } else {
            return super.get(result, property);
        }
    }
}
