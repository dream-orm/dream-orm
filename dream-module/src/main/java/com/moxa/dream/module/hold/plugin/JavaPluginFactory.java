package com.moxa.dream.module.hold.plugin;

import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.BaseReflectHandler;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public abstract class JavaPluginFactory extends AbstractPluginFactory {
    Map<Integer, Class[]> interfaceMap = new HashMap<>();

    public Object plugin(Object origin, Object target, Interceptor interceptor) {
        int hash = hash(origin, interceptor);
        Class[] interfaces = interfaceMap.get(hash);
        if (interfaces == null) {
            List<Class> interfaceList = getAllInterface(origin);
            Set<Method> methods;
            try {
                methods = interceptor.methods();
            } catch (Exception e) {
                throw new PluginException(e);
            }
            if (!ObjectUtil.isNull(interfaceList) && !ObjectUtil.isNull(methods)) {
                List<Class> filterInterfaceList = new ArrayList<>();
                Set<? extends Class<?>> classSet = methods
                        .stream()
                        .map(method -> method.getDeclaringClass())
                        .collect(Collectors.toSet());
                for (Class interfaceType : interfaceList) {
                    if (classSet.contains(interfaceType)) {
                        filterInterfaceList.add(interfaceType);
                        break;
                    }
                }
                interfaces = filterInterfaceList.toArray(new Class[0]);
            } else interfaces = new Class[0];
        }
        if (interfaces.length > 0) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, (proxy, method, args) -> {
                        if (interceptor.methods().contains(method)) {
                            return interceptor.interceptor(new JavaInvocation(target, method, args));
                        } else {
                            return method.invoke(target, args);
                        }
                    }
            );
        } else
            return target;

    }

    protected int hash(Object origin, Interceptor interceptor) {
        return ObjectUtil.hash(origin.getClass(), interceptor.getClass());
    }

    protected List<Class> getAllInterface(Object origin) {
        return ReflectUtil.find(origin.getClass(), new BaseReflectHandler<Class>() {
            @Override
            public List<Class> doHandler(Class type) {
                if (type.isInterface())
                    return Arrays.asList(type);
                else
                    return null;
            }
        });
    }
}
