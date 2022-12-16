package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.Invoker;

public interface InvokerFactory {
    void addInvoker(Invoker invoker);

    Invoker getInvoker(String function, String namespace);
}
