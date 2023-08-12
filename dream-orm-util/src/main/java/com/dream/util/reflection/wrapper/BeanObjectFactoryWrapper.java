package com.dream.util.reflection.wrapper;

import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflection.factory.BeanObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
        if(setterCaller==null){
            throw new DreamRunTimeException(result.getClass().getName()+"没有属性"+property+" setter方法");
        }
        setterCaller.call(result, value);
    }

    public Object get(Object result, String property) {
        PropertyGetter propertyGetter = getterMap.get(property);
        if(propertyGetter==null){
            throw new DreamRunTimeException(result.getClass().getName()+"没有属性"+property+" getter方法");
        }
        return propertyGetter.apply(result);
    }

    private void lambdaProperty(Class type) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(type, Object.class);
        } catch (IntrospectionException e) {
            throw new DreamRunTimeException(e.getMessage(), e);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            PropertyGetter<Object, ?> propertyGetter = lambdaPropertyGetter(type, descriptor);
            if (propertyGetter != null) {
                getterMap.put(descriptor.getName(), propertyGetter);
            }
            PropertySetterCaller<Object> propertySetterCaller = lambdaPropertySetter(type, descriptor);
            if (propertySetterCaller != null) {
                setterMap.put(descriptor.getName(), propertySetterCaller);
            }
        }
    }

    private PropertyGetter<Object, ?> lambdaPropertyGetter(Class type, PropertyDescriptor descriptor) {
        Method readMethod = descriptor.getReadMethod();
        if (readMethod != null) {
            Class<?> propertyType = descriptor.getPropertyType();
            String getFunName = readMethod.getName();
            final MethodHandles.Lookup caller = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(propertyType, type);
            final CallSite site;

            try {
                site = LambdaMetafactory.altMetafactory(caller,
                        "apply",
                        MethodType.methodType(PropertyGetter.class),
                        methodType.erase().generic(),
                        caller.findVirtual(type, getFunName, MethodType.methodType(propertyType)),
                        methodType, FLAG_SERIALIZABLE);
                return (PropertyGetter<Object, ?>) site.getTarget().invokeExact();
            } catch (Throwable e) {
                throw new DreamRunTimeException(e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    private PropertySetterCaller<Object> lambdaPropertySetter(Class type, PropertyDescriptor descriptor) {
        Method writeMethod = descriptor.getWriteMethod();
        if (writeMethod != null) {
            Class<?> propertyType = descriptor.getPropertyType();
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
        } else {
            return null;
        }
    }

    private Class<?> getObjectTypeWhenPrimitive(Class<?> propertyType) {
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
