package com.dream.util.reflection.factory;

import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflection.wrapper.CollectionObjectFactoryWrapper;

import java.util.Collection;
import java.util.List;

public class CollectionObjectFactory implements ObjectFactory {

    protected CollectionObjectFactoryWrapper factoryWrapper;
    Collection result;

    public CollectionObjectFactory(Collection target) {
        this(target, null);
    }

    public CollectionObjectFactory(Collection target, CollectionObjectFactoryWrapper factoryWrapper) {
        this.result = target;
        this.factoryWrapper = factoryWrapper;
    }

    @Override
    public void set(String property, Object value) {
        result.add(value);
    }

    @Override
    public Object get(String property) {
        return get(result, property);
    }

    @Override
    public Object getObject() {
        return result;
    }

    protected Object get(Collection result, String property) {
        if (property != null && Character.isDigit(property.charAt(0))) {
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
            return result;
        }
    }
}
