package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.exception.AntlrRunTimeException;
import com.moxa.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;

public class AntlrInvokerFactory implements InvokerFactory {
    protected Map<String, Map<String, Invoker>> functionInvokerMap = new HashMap<>(16);

    @Override
    public void addInvokers(Invoker... invokers) {
        if (invokers != null && invokers.length > 0) {
            for (Invoker invoker : invokers) {
                addInvoker(invoker);
            }
        }
    }

    protected void addInvoker(Invoker invoker) {
        String function = invoker.function();
        String namespace = invoker.namespace();
        Map<String, Invoker> invokerMap = functionInvokerMap.get(function);
        if (invokerMap == null) {
            invokerMap = new HashMap<>(16);
            functionInvokerMap.put(function, invokerMap);
        }
        if (namespace == null || namespace.trim().length() == 0) {
            namespace = Invoker.DEFAULT_NAMESPACE;
        }
        if (invokerMap.containsKey(namespace)) {
            throw new AntlrRunTimeException("@" + function + ":" + namespace + "已经存在");
        }
        invokerMap.put(namespace, invoker);
    }

    @Override
    public Invoker getInvoker(String function, String namespace) {
        Map<String, Invoker> invokerMap = functionInvokerMap.get(function);
        if (invokerMap == null) {
            return null;
        }
        Invoker invoker;
        if (namespace == null || namespace.trim().length() == 0) {
            invoker = invokerMap.get(Invoker.DEFAULT_NAMESPACE);
            if (invoker == null && invokerMap.size() == 1) {
                invoker = invokerMap.values().iterator().next();
            }
        } else {
            invoker = invokerMap.get(namespace);
        }
        if (invoker == null) {
            return null;
        }
        return invoker;
    }
}
