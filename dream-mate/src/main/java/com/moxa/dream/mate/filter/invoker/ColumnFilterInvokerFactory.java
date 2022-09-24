package com.moxa.dream.mate.filter.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class ColumnFilterInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "columnFilter";
    public static final String COLUMN_FILTER = "columnFilter";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case COLUMN_FILTER:
                return new ColumnFilterInvoker();
        }
        return null;
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
