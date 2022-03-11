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
    public void set(PropertyToken propertyToken, Object value) throws WrapperException {
        String name = propertyToken.getName();
        if (name.startsWith("[") && name.endsWith("]")) {
            int index = Integer.valueOf(name.substring(1, name.length() - 1));
            if (propertyToken.hasNext()) {
                ObjectWrapper resultWrapper;
                Object result = ((List) object).get(index);
                if (result == null) {
                    result = new HashMap<>();
                    ((List) object).set(index, result);
                }
                resultWrapper = wrapper(result);
                resultWrapper.set(propertyToken.next(), value);
            } else {
                if (index < 0) {
                    if (value instanceof Collection) {
                        object.addAll((Collection) value);
                    } else
                        object.add(value);
                } else {
                    ((List) object).set(index, value);
                }
            }
        } else {
            if (beanWrapper == null) {
                beanWrapper = new BeanWrapper(new ReflectClass(object));
            }
            beanWrapper.set(propertyToken, value);
        }
    }

    @Override
    public Object getObject() {
        return object;
    }
}
