package com.moxa.dream.util.reflect;

import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableReflectHandler implements ReflectHandler {
    private final Class rootClass;
    private final Map<String, Class> variableMap = new HashMap<>();

    public VariableReflectHandler(Class rootClass) {
        this.rootClass = rootClass;
    }

    @Override
    public List doHandler(Class type) {
        Type genericSuperclass = type.getGenericSuperclass();
        if (genericSuperclass != null) {
            handlerVariable(genericSuperclass);
        }
        Type[] genericInterfaces = type.getGenericInterfaces();
        if (genericInterfaces != null) {
            for (Type genericType : genericInterfaces) {
                handlerVariable(genericType);
            }
        }
        return null;
    }

    @Override
    public List<Class> goHandler(Class type) {
        List<Class> list = new ArrayList();
        Class[] interfaces = type.getInterfaces();
        if (!ObjectUtil.isNull(interfaces)) {
            for (int i = 0; i < interfaces.length; i++) {
                if (rootClass.isAssignableFrom(interfaces[i])) {
                    list.add(interfaces[i]);
                }
            }
        }
        Class superclass = type.getSuperclass();
        if (superclass != null && rootClass.isAssignableFrom(superclass))
            list.add(superclass);
        return list;
    }

    protected void handlerVariable(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Class typeClass = (Class) parameterizedType.getRawType();
            TypeVariable[] typeParameters = typeClass.getTypeParameters();
            for (int i = 0; i < typeParameters.length; i++) {
                String name = typeParameters[i].getName();
                if (!variableMap.containsKey(name)) {
                    if (actualTypeArguments[i] instanceof Class) {
                        variableMap.put(name, (Class) actualTypeArguments[i]);
                    } else if (actualTypeArguments[i] instanceof ParameterizedType) {
                        handlerVariable(actualTypeArguments[i]);
                    }
                }
            }
        }
    }

    public Map<String, Class> getVariableMap() {
        return variableMap;
    }
}
