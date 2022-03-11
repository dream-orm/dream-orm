package com.moxa.dream.util.reflect;


import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

class ReflectLoader {
    public Class<?> resolveInterface(Class<?> type) throws ReflectException {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            if (type.isAssignableFrom(ArrayList.class))
                type = ArrayList.class;
            else if (type.isAssignableFrom(HashMap.class))
                type = HashMap.class;
            else if (type.isAssignableFrom(HashSet.class))
                type = HashSet.class;
            else if (type.isAssignableFrom(LinkedList.class))
                type = LinkedList.class;
            else
                throw new ReflectException("抽象类'" + type + "'未发现实现类");
        }
        return type;
    }

    public <T> T create(Class<T> type, Object[] objectList, boolean force) throws ReflectException {
        if (ReflectUtil.isBaseClass(type)) {
            return null;
        }
        type = (Class<T>) resolveInterface(type);
        return instantiateClass(type, objectList, force);
    }

    public <T> T instantiateClass(Class<T> type, Object[] constructorArgs, boolean force) throws ReflectException {
        Class[] constructorArgTypes = null;
        try {
            if (constructorArgs == null) {
                Constructor<T> constructor = type.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } else {
                Optional<Constructor<?>> optional = Arrays.stream(type.getDeclaredConstructors()).filter(constructor -> {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes.length == constructorArgs.length) {
                        int i;
                        for (i = 0; i < parameterTypes.length; i++) {
                            Class<?> parameterType = parameterTypes[i];
                            if (!ReflectUtil.castClass(parameterType).isAssignableFrom(ReflectUtil.castClass(constructorArgs[i].getClass())))
                                break;
                        }
                        if (i == parameterTypes.length)
                            return true;
                    }
                    return false;
                }).findFirst();
                if (optional.isPresent()) {
                    Constructor<?> constructor = optional.get();
                    constructorArgTypes = constructor.getParameterTypes();
                    constructor.setAccessible(true);
                    return (T) constructor.newInstance(constructorArgs);
                } else
                    throw new Exception();
            }
        } catch (Exception e) {
            if (force) {
                Optional<Constructor<?>> paramConstructor = Arrays.stream(type.getDeclaredConstructors()).min(Comparator.comparingInt(Constructor::getParameterCount));
                Constructor<?> constructor = paramConstructor.get();
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameterArgs = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (Number.class.isAssignableFrom(ReflectUtil.castClass(parameterTypes[i])))
                        parameterArgs[i] = 0;
                    else
                        parameterArgs[i] = null;
                }
                try {
                    return (T) constructor.newInstance(parameterArgs);
                } catch (Exception ex) {
                    e = ex;

                }
            }
            String argTypes = Arrays.asList(Optional.ofNullable(constructorArgTypes).orElse(new Class[0]))
                    .stream().map(Class::getSimpleName).collect(Collectors.joining(","));
            String argValues = Arrays.asList(Optional.ofNullable(constructorArgs).orElse(new Class[0]))
                    .stream().map(String::valueOf).collect(Collectors.joining(","));
            throw new ReflectException("'" + type.getName() + "'创建失败,参数类型(" + argTypes + "),参数值(" + argValues + ")", e);

        }
    }

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
