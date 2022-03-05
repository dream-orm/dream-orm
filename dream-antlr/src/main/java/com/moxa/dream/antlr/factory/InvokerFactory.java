package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.Invoker;

public interface InvokerFactory {

    Invoker create(String function);

    String namespace();
}
