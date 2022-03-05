package com.moxa.dream.util.wrapper;


public class BeanWrapper extends ObjectWrapper {
    private ReflectClass object;

    public BeanWrapper(ReflectClass object) {
        this.object = object;
    }

    @Override
    public Object get(PropertyToken propertyToken) {
        String name = propertyToken.getName();
        Object value = object.get(name);
        if (propertyToken.hasNext())
            return wrapper(value).get(propertyToken.next());
        else
            return value;
    }

    @Override
    public Object set(PropertyToken propertyToken, Object value) throws WrapperException {
        String name = propertyToken.getName();
        if (name == null) {
            if (value == null)
                throw new WrapperException("替换bean对象为空");
            Object target = object.getTarget();
            if (value.getClass().equals(value.getClass()))
                object.setTarget(value);
            else throw new WrapperException("替换的bean对象必须是原bean对象相同");
            return target;
        } else if (propertyToken.hasNext()) {
            ObjectWrapper resultWrapper;
            Object result = object.get(name);
            if (result == null) {
                resultWrapper = super.wrapper(object.getField(name).getGenericType());
                result = resultWrapper.getObject();
                object.set(name, result);
            } else
                resultWrapper = wrapper(result);
            return resultWrapper.set(propertyToken.next(), value);
        } else
            return object.set(name, value);
    }

    @Override
    public Object getObject() {
        return object.getTarget();
    }
}