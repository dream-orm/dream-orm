package com.dream.antlr.config;

import com.dream.antlr.exception.AntlrRunTimeException;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;

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

    public Invoker getInvoker(String function) {
        Invoker invoker;
        invoker = invokerMap.get(function);
        if (invoker == null) {
            invoker = invokerFactory.getInvoker(function);
            if (invoker == null) {
                throw new AntlrRunTimeException("@" + function + "不存在");
            }
            invokerMap.put(function, invoker);
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
