package com.moxa.dream.antlr.config;

import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.exception.AntlrRunTimeException;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Assist {
    private Map<Class, Object> customObjMap;
    private Map<String, Invoker> invokerMap = new HashMap<>();

    private Map<Class, Invoker> invokerTypeMap = new HashMap<>();
    private InvokerFactory invokerFactory;

    public Assist(InvokerFactory invokerFactory, Map<Class, Object> customObjMap) {
        this.invokerFactory = invokerFactory;
        this.customObjMap = customObjMap;
    }

    public Invoker getInvoker(String function, String namespace) {
        Invoker invoker;
        if (namespace == null || namespace.trim().length() == 0) {
            namespace = Invoker.DEFAULT_NAMESPACE;
        }
        String name = function + ":" + namespace;
        invoker = invokerMap.get(name);
        if (invoker == null) {
            invoker = invokerFactory.getInvoker(function, namespace).newInstance();
            invoker.init(this);
            invokerMap.put(name, invoker);
            invokerTypeMap.put(invoker.getClass(), invoker);
        }
        return invoker;
    }

    public <T extends Invoker> T getInvoker(Class<? extends Invoker> invokerType) {
        Invoker invoker = invokerTypeMap.get(invokerType);
        if (invoker == null) {
            invoker = invokerFactory.getInvoker(invokerType).newInstance();
            invoker.init(this);
            invokerMap.put(invoker.function() + ":" + invoker.namespace(), invoker);
            invokerTypeMap.put(invokerType, invoker);
        }
        return (T) invoker;
    }

    public <T> T getCustom(Class<T> type) {
        return (T) customObjMap.get(type);
    }

    public <T> void setCustom(Class<T> type, T value) {
        if (customObjMap == null) {
            customObjMap = new HashMap<>();
        }
        customObjMap.put(type, value);
    }
}
