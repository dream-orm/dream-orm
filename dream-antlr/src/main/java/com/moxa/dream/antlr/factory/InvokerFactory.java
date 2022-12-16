package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.Invoker;

public interface InvokerFactory {
    void addInvokers(Invoker... invokers);

    Invoker getInvoker(String function, String namespace);
}
