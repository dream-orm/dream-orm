package com.moxa.dream.antlr.config;

import com.moxa.dream.antlr.exception.AntlrRunTimeException;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;

public class Assist {
    private Map<Class, Object> customObjMap;
    private Map<String, Invoker> invokerMap = new HashMap<>(16);
    private InvokerFactory invokerFactory;

    public Assist(InvokerFactory invokerFactory, Map<Class, Object> customObjMap) {
        this.invokerFactory = invokerFactory;
        this.customObjMap = customObjMap;
    }

    public Invoker getInvoker(String function, String namespace) {
        Invoker invoker;
        String name;
        if (namespace == null || namespace.trim().length() == 0) {
            name = function;
        } else {
            name = function + ":" + namespace;
        }
        invoker = invokerMap.get(name);
        if (invoker == null) {
            invoker = invokerFactory.getInvoker(function, namespace);
            if (invoker != null) {
                invoker = invoker.newInstance();
            }
            if (invoker == null) {
                throw new AntlrRunTimeException("@" + name + "不存在");
            }
            invokerMap.put(name, invoker);
            String invokerName = invoker.function() + ":" + invoker.namespace();
            if (!invokerName.equals(name)) {
                invokerMap.put(invokerName, invoker);
            }
            invoker.init(this);
        }
        return invoker;
    }

    public <T> T getCustom(Class<T> type) {
        return (T) customObjMap.get(type);
    }

    public <T> void setCustom(Class<T> type, T value) {
        if (customObjMap == null) {
            customObjMap = new HashMap<>(4);
        }
        customObjMap.put(type, value);
    }
}
