package com.dream.antlr.factory;

import com.dream.antlr.exception.AntlrRunTimeException;
import com.dream.antlr.invoker.Invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AntlrInvokerFactory implements InvokerFactory {
    protected Map<String, Supplier<Invoker>> functionInvokerMap = new HashMap<>(16);

    @Override
    public void addInvoker(String function, Supplier<Invoker> invoker) {
        if (functionInvokerMap.containsKey(function)) {
            throw new AntlrRunTimeException("@" + function + "已经存在");
        }
        functionInvokerMap.put(function, invoker);
    }

    @Override
    public Invoker getInvoker(String functionName) {
        Supplier<Invoker> supplier = functionInvokerMap.get(functionName);
        if (supplier != null) {
            return supplier.get();
        }
        return null;
    }
}
