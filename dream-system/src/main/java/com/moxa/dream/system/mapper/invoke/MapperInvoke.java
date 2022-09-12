package com.moxa.dream.system.mapper.invoke;

import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface MapperInvoke {
    Object invoke(MethodInfo methodInfo, Object arg) throws Exception;

    default Object invoke(Class type, Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            return constructor.newInstance(type)
                    .in(type)
                    .unreflectSpecial(method, type)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } else {
            throw new DreamRunTimeException("接口方法不支持调用，方法名：" + type.getName() + "." + method.getName());
        }
    }
}
