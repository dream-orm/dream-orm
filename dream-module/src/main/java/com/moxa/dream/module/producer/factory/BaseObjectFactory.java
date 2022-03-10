package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.PropertyInfo;
import com.moxa.dream.util.common.ObjectUtil;

public class BaseObjectFactory implements ObjectFactory {
    private Class type;
    private Object result;
    public BaseObjectFactory(Class type) {
        this.type = type;
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        if (value != null) {
            ObjectUtil.requireTrue(type.isAssignableFrom(value.getClass()),"");
        }
        this.result=value;
    }

    @Override
    public Object getObject() {
        return result;
    }
}
