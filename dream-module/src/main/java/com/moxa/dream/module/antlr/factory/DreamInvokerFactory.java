package com.moxa.dream.module.antlr.factory;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.module.antlr.invoker.$LimitInvoker;
import com.moxa.dream.module.antlr.invoker.$OffSetInvoker;
import com.moxa.dream.module.antlr.invoker.AllInvoker;
import com.moxa.dream.module.antlr.invoker.TableInvoker;

public class DreamInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "dream";
    public static final String ALL = "all";
    public static final String $LIMIT = "$limit";
    public static final String $OFFSET = "$offset";
    public static final String TABLE = "table";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case ALL:
                return new AllInvoker();
            case $LIMIT:
                return new $LimitInvoker();
            case $OFFSET:
                return new $OffSetInvoker();
            case TABLE:
                return new TableInvoker();
            default:
                return null;
        }
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
