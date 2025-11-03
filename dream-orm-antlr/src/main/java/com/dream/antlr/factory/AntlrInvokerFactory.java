package com.dream.antlr.factory;

import com.dream.antlr.exception.AntlrRunTimeException;
import com.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;

public class AntlrInvokerFactory implements InvokerFactory {
    protected Map<String, Invoker> functionInvokerMap = new HashMap<>(16);

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
        if (functionInvokerMap.containsKey(function)) {
            throw new AntlrRunTimeException("@" + function + "已经存在");
        }
        functionInvokerMap.put(function, invoker);
    }

    @Override
    public Invoker getInvoker(String functionName) {
        return functionInvokerMap.get(functionName);
    }
}
