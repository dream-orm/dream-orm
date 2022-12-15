package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.exception.AntlrRunTimeException;
import com.moxa.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;

public class AntlrInvokerFactory implements InvokerFactory {
    protected Map<String, Map<String, Invoker>> functionInvokerMap = new HashMap<>(16);
    protected Map<Class, Invoker> invokerTypeMap = new HashMap<>(16);

    @Override
    public void addInvoker(Invoker invoker) {
        String function = invoker.function();
        String namespace = invoker.namespace();
        Map<String, Invoker> invokerMap = functionInvokerMap.get(function);
        if (invokerMap == null) {
            invokerMap = new HashMap<>();
            functionInvokerMap.put(function, invokerMap);
        }
        if (namespace == null || namespace.trim().length() == 0) {
            namespace = Invoker.DEFAULT_NAMESPACE;
        }
        if (invokerMap.containsKey(namespace)) {
            throw new AntlrRunTimeException("@" + function + ":" + namespace + "已经存在");
        }
        invokerMap.put(namespace, invoker);
        invokerTypeMap.put(invoker.getClass(), invoker);
    }

    @Override
    public Invoker getInvoker(String function, String namespace) {
        Map<String, Invoker> invokerMap = functionInvokerMap.get(function);
        if (invokerMap == null) {
            throw new AntlrRunTimeException("@函数" + function + "不存在");
        }
        if (namespace == null || namespace.trim().length() == 0) {
            namespace = Invoker.DEFAULT_NAMESPACE;
        }
        Invoker invoker = invokerMap.get(namespace);
        if (invoker == null) {
            throw new AntlrRunTimeException("@" + function + ":" + namespace + "不存在");
        }
        return invoker;
    }

    @Override
    public <T extends Invoker> T getInvoker(Class<T> invokerType) {
        Invoker invoker = invokerTypeMap.get(invokerType);
        if (invoker == null) {
            throw new AntlrRunTimeException(invokerType.getName() + "不存在");
        }
        return (T) invoker;
    }
}
