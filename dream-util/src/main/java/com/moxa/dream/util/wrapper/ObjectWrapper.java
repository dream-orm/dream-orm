package com.moxa.dream.util.wrapper;

import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.Collection;
import java.util.Map;

public abstract class ObjectWrapper {

    public static ObjectWrapper wrapper(Object object) {
        ObjectWrapper wrapper;
        if (object == null)
            wrapper = new NullWrapper();
        else if (object instanceof ObjectWrapper) {
            wrapper = (ObjectWrapper) object;
        } else if (object instanceof Map) {
            wrapper = new MapWrapper((Map) object);
        } else if (object instanceof Collection) {
            wrapper = new CollectionWrapper((Collection) object);
        } else if(ReflectUtil.isBaseClass(object.getClass())){
            wrapper=new BasicWrapper(object);
        }else{
            wrapper = new BeanWrapper(ReflectClass.newInstance(object));
        }
        return wrapper;
    }

    public void set(String property, Object value) {
        set(new PropertyToken(property), value);
    }

    public Object get(String property) throws WrapperException {
        return get(new PropertyToken(property));
    }

    public abstract Object getObject();

    protected abstract void set(PropertyToken propertyToken, Object value) throws WrapperException;

    protected abstract Object get(PropertyToken propertyToken);

}
