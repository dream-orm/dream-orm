package com.moxa.dream.util.reflect;


import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

class ReflectLoader {

    public <T> List<T> find(Class type, ReflectHandler<T> reflectHandler) {
        return find(type, reflectHandler, new HashSet<>());
    }

    public <T> List<T> find(Class type, ReflectHandler<T> reflectHandler, HashSet<Class> hashSet) {
        if (type == null)
            return null;
        List<T> resultList = new ArrayList<>();
        List<T> list = reflectHandler.doHandler(type);
        if (list != null) {
            resultList.addAll(list);
        }
        hashSet.add(type);
        List<Class> classes = reflectHandler.goHandler(type);
        if (classes != null) {
            for (Class typeClass : classes) {
                if (!hashSet.contains(typeClass))
                    resultList.addAll(find(typeClass, reflectHandler, hashSet));
            }
        }
        return resultList;
    }

    public Type getGenericType(Class type, Method method) {
        return getGenericType(type, method.getDeclaringClass(), method.getGenericReturnType());
    }

    public Type getGenericType(Class type, Field field) {
        return getGenericType(type, field.getDeclaringClass(), field.getGenericType());
    }

    protected Type getGenericType(Class childType, Class superType, Type genericType) {
        if (genericType instanceof TypeVariable) {
            String name = ((TypeVariable<?>) genericType).getName();
            Map<String, Class> variableTypeMap = findVariableMap(childType, superType);
            Class type = variableTypeMap.get(name);
            return type;
        } else {
            return genericType;
        }
    }

    public Map<String, Class> findVariableMap(Class childClass, Class superClass) {
        VariableReflectHandler variableReflectHandler = new VariableReflectHandler(superClass);
        find(childClass, variableReflectHandler);
        return variableReflectHandler.getVariableMap();
    }
}
