package com.moxa.dream.util.wrapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CollectionWrapper extends ObjectWrapper {
    private Collection object;
    private BeanWrapper beanWrapper;

    public CollectionWrapper(Collection object) {
        this.object = object;
    }


    @Override
    public Object get(PropertyToken propertyToken) {
        if (!(object instanceof List)) {
            throw new RuntimeException(object.getClass().getName() + "不支持Get方法");
        }
        String name = propertyToken.getName();
        if (name != null && name.startsWith("[") && name.endsWith("]")) {
            int index = Integer.valueOf(name.substring(1, name.length() - 1));
            Object o = ((List) object).get(index);
            if (propertyToken.hasNext()) {
                return wrapper(o).get(propertyToken.next());
            } else
                return o;
        } else {
            if (beanWrapper == null) {
                beanWrapper = new BeanWrapper(new ReflectClass(object));
            }
            return beanWrapper.get(propertyToken);
        }
    }

    @Override
    public Object set(PropertyToken propertyToken, Object value) throws WrapperException {
        String name = propertyToken.getName();
        if (name.startsWith("[") && name.endsWith("]")) {
            int index = Integer.valueOf(name.substring(1, name.length() - 1));
            if (propertyToken.hasNext()) {
                if (object instanceof List) {
                    ObjectWrapper resultWrapper;
                    Object result = ((List) object).get(index);
                    if (result == null) {
                        result = new HashMap<>();
                        ((List) object).set(index, result);
                    }
                    resultWrapper = wrapper(result);
                    return resultWrapper.set(propertyToken.next(), value);
                }
                throw new WrapperException("类对象:" + object.getClass().getName() + "，不支持Set方法");
            } else {
                if (index < 0) {
                    if (value instanceof Collection) {
                        object.addAll((Collection) value);
                    } else
                        object.add(value);
                    return null;
                } else {
                    if (object instanceof List) {
                        Object result = ((List) object).get(index);
                        ((List) object).set(index, value);
                        return result;
                    }
                    throw new WrapperException("类对象:" + object.getClass().getName() + "，不支持Set方法");
                }
            }
        } else throw new WrapperException("类对象：" + object.getClass().getName() + "，下标：" + name + "必须是整数");
    }

    @Override
    public Object getObject() {
        return object;
    }
}
