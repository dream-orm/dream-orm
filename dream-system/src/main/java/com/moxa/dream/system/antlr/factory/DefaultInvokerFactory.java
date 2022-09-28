package com.moxa.dream.system.antlr.factory;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;

public class DefaultInvokerFactory implements InvokerFactory {
    private Map<String, Invoker> invokerMap = new HashMap<>();

    public void addInvoker(String function, Invoker invoker) {
        invokerMap.put(function, invoker);
    }

    @Override
    public Invoker create(String function) {
        return invokerMap.get(function);
    }

    @Override
    public String namespace() {
        return null;
    }
}
