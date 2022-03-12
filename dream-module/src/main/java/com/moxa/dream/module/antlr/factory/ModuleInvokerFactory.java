package com.moxa.dream.module.antlr.factory;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.module.antlr.invoker.$LimitInvoker;
import com.moxa.dream.module.antlr.invoker.$OffSetInvoker;
import com.moxa.dream.module.antlr.invoker.AllInvoker;

public class ModuleInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "module";
    public static final String ALL = "all";
    public static final String $LIMIT = "$limit";
    public static final String $OFFSET = "$offset";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case ALL:
                return new AllInvoker();
            case $LIMIT:
                return new $LimitInvoker();
            case $OFFSET:
                return new $OffSetInvoker();
            default:
                return null;
        }
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
