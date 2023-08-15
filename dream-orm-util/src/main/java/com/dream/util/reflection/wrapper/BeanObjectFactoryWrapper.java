package com.dream.util.reflection.wrapper;

import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflection.factory.BeanObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.io.Serializable;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BeanObjectFactoryWrapper implements ObjectFactoryWrapper {
    public static final Integer FLAG_SERIALIZABLE = 1;
    private Supplier<Object> objectSupplier;
    private Map<String, PropertyGetter> getterMap = new HashMap<>(8);
    private Map<String, PropertySetterCaller> setterMap = new HashMap<>(8);

    public BeanObjectFactoryWrapper(Class type) {
        this.objectSupplier = this.constructorCreator(type);
        this.lambdaProperty(type);
    }

    protected Supplier<Object> constructorCreator(Class type) {
        try {
            MethodType constructorType = MethodType.methodType(void.class);
            MethodHandles.Lookup caller = MethodHandles.lookup();
            MethodHandle constructorHandle = caller.findConstructor(type, constructorType);
            CallSite site = LambdaMetafactory.altMetafactory(caller,
                    "get",
                    MethodType.methodType(Supplier.class),
                    constructorHandle.type().generic(),
                    constructorHandle,
                    constructorHandle.type(), FLAG_SERIALIZABLE);
            return (Supplier<Object>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new DreamRunTimeException(e.getMessage(), e);
        }
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new BeanObjectFactory(objectSupplier.get(), this);
        } else {
            return new BeanObjectFactory(target, this);
        }
    }

    public void set(Object result, String property, Object value) {
        PropertySetterCaller setterCaller = setterMap.get(property);
        if (setterCaller == null) {
            throw new DreamRunTimeException(result.getClass().getName() + "没有属性" + property + " setter方法");
        }
        setterCaller.call(result, value);
    }

    public Object get(Object result, String property) {
        PropertyGetter propertyGetter = getterMap.get(property);
        if (propertyGetter == null) {
            throw new DreamRunTimeException(result.getClass().getName() + "没有属性" + property + " getter方法");
        }
        return propertyGetter.apply(result);
    }

    private void lambdaProperty(Class type) {
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                PropertySetterCaller<Object> propertySetterCaller = lambdaPropertySetter(type, method);
                setterMap.put(property, propertySetterCaller);
            } else if (name.startsWith("get") && method.getParameterCount() == 0) {
                String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                PropertyGetter<Object, ?> propertyGetter = lambdaPropertyGetter(type, method);
                getterMap.put(property, propertyGetter);
            } else if (name.startsWith("is") && method.getParameterCount() == 0) {
                String property = Character.toLowerCase(name.charAt(2)) + name.substring(3);
                PropertyGetter<Object, ?> propertyGetter = lambdaPropertyGetter(type, method);
                getterMap.put(property, propertyGetter);
            }
        }
    }

    private PropertyGetter<Object, ?> lambdaPropertyGetter(Class type, Method readMethod) {
        Class<?> returnType = readMethod.getReturnType();
        String getFunName = readMethod.getName();
        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(returnType, type);
        try {
            CallSite site = LambdaMetafactory.altMetafactory(caller,
                    "apply",
                    MethodType.methodType(PropertyGetter.class),
                    methodType.erase().generic(),
                    caller.findVirtual(type, getFunName, MethodType.methodType(returnType)),
                    methodType, FLAG_SERIALIZABLE);
            return (PropertyGetter<Object, ?>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new DreamRunTimeException(e.getMessage(), e);
        }

    }

    private PropertySetterCaller<Object> lambdaPropertySetter(Class type, Method writeMethod) {
        Class<?> propertyType = writeMethod.getParameterTypes()[0];
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);

        Class<?> lambdaPropertyType = getObjectTypeWhenPrimitive(propertyType);
        String getFunName = writeMethod.getName();
        try {
            MethodType instantiatedMethodType = MethodType.methodType(void.class, type, lambdaPropertyType);
            MethodHandle target = caller.findVirtual(type, getFunName, setter);
            MethodType samMethodType = MethodType.methodType(void.class, Object.class, Object.class);
            CallSite site = LambdaMetafactory.metafactory(
                    caller,
                    "apply",
                    MethodType.methodType(PropertySetter.class),
                    samMethodType,
                    target,
                    instantiatedMethodType
            );
            PropertySetter<Object, Object> objectPropertyVoidSetter = (PropertySetter<Object, Object>) site.getTarget().invokeExact();
            return objectPropertyVoidSetter::apply;
        } catch (Throwable e) {
            throw new DreamRunTimeException(e.getMessage(), e);
        }
    }

    private Class<?> getObjectTypeWhenPrimitive(Class<?> propertyType) {
        if (propertyType.isPrimitive()) {
            switch (propertyType.getName()) {
                case "boolean":
                    return Boolean.class;
                case "byte":
                    return Byte.class;
                case "short":
                    return Short.class;
                case "int":
                    return Integer.class;
                case "long":
                    return Long.class;
                case "float":
                    return Float.class;
                case "double":
                    return Double.class;
                case "char":
                    return Character.class;
                default:
                    return propertyType;
            }
        }
        return propertyType;
    }

    @FunctionalInterface
    interface PropertySetter<T1, T2> extends Serializable {
        void apply(T1 t, T2 value);
    }

    @FunctionalInterface
    public interface PropertyGetter<T, R> extends Serializable {
        R apply(T t);
    }

    public interface PropertySetterCaller<T> {
        void call(T t, Object value);
    }

}
