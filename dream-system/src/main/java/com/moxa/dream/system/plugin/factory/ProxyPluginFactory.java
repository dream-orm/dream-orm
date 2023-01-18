package com.moxa.dream.system.plugin.factory;

import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.system.plugin.invocation.JavaInvocation;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class ProxyPluginFactory extends AbstractPluginFactory {
    Map<Integer, Interface> interfaceMap = new HashMap<>(4);

    @Override
    public Object plugin(Object origin, Object target, Interceptor interceptor) {
        int hash = hash(origin, interceptor);
        Interface anInterface = interfaceMap.get(hash);
        if (anInterface == null) {
            synchronized (this) {
                anInterface = interfaceMap.get(hash);
                if (anInterface == null) {
                    Class[] interfaces;
                    List<Class> interfaceList = getAllInterface(origin);
                    Set<Method> methods;
                    try {
                        methods = interceptor.methods();
                    } catch (Exception e) {
                        throw new DreamRunTimeException("获取拦截方法失败", e);
                    }
                    if (!ObjectUtil.isNull(interfaceList) && !ObjectUtil.isNull(methods)) {
                        List<Class> filterInterfaceList = new ArrayList<>();
                        Set<? extends Class<?>> classSet = methods.stream().map(method -> method.getDeclaringClass()).collect(Collectors.toSet());
                        for (Class interfaceType : interfaceList) {
                            if (classSet.contains(interfaceType)) {
                                filterInterfaceList.add(interfaceType);
                                break;
                            }
                        }
                        interfaces = filterInterfaceList.toArray(new Class[0]);
                    } else {
                        interfaces = new Class[0];
                    }
                    anInterface = new Interface(interfaces, methods);
                    interfaceMap.put(hash, anInterface);
                }
            }
        }
        Set<Method> methods = anInterface.methods;
        if (anInterface.interfaces.length > 0) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), anInterface.interfaces, (proxy, method, args) -> {
                try {
                    if (methods.contains(method)) {
                        return interceptor.interceptor(new JavaInvocation(target, method, args));
                    } else {
                        return method.invoke(target, args);
                    }
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            });
        } else {
            return target;
        }
    }

    protected int hash(Object origin, Interceptor interceptor) {
        return ObjectUtil.hash(origin.getClass(), interceptor.getClass());
    }

    protected List<Class> getAllInterface(Object origin) {
        return ReflectUtil.find(origin.getClass(), type -> {
            if (type.isInterface()) {
                return Arrays.asList(type);
            } else {
                return null;
            }
        });
    }

    class Interface {
        private Class[] interfaces;
        private Set<Method> methods;

        public Interface(Class[] interfaces, Set<Method> methods) {
            this.interfaces = interfaces;
            this.methods = methods;
        }
    }
}
