package com.moxa.dream.util.wrapper;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CollectionWrapper extends ObjectWrapper {
    private Collection object;
    private Type type;
    private BeanWrapper beanWrapper;

    public CollectionWrapper(Collection object, Type type) {
        this.object = object;
        this.type = type;
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
                return super.wrapper(o).get(propertyToken.next());
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
        if (name == null) {
            if (value == null)
                throw new WrapperException("替换集合对象为空");
            if (object.getClass().equals(value.getClass())) {
                Object oldObject = object;
                object = (Collection) value;
                return oldObject;
            } else
                throw new WrapperException("原集合对象'" + object.getClass().getName() + "'" + value.getClass().getName() + "'类型不相同");
        } else if (name.startsWith("[") && name.endsWith("]")) {
            int index = Integer.valueOf(name.substring(1, name.length() - 1));
            if (propertyToken.hasNext()) {
                if (object instanceof List) {
                    ObjectWrapper resultWrapper;
                    Object result = ((List) object).get(index);
                    if (result == null) {
                        resultWrapper = wrapper(type);
                        result = resultWrapper.getObject();
                        ((List) object).set(index, result);
                    } else {
                        resultWrapper = wrapper(result);
                    }
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

    public void add(PropertyToken propertyToken, Object value) throws WrapperException {
        if (propertyToken.hasNext()) {
            ObjectWrapper wrapper = super.wrapper(type);
            wrapper.set(propertyToken.next(), value);
            Object objectValue = wrapper.getObject();
            object.add(objectValue);
        } else
            object.add(value);
    }


    @Override
    public Object getObject() {
        return object;
    }
}
