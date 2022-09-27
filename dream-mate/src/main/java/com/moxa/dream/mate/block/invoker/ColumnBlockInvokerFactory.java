package com.moxa.dream.mate.block.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class ColumnBlockInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "dream-mate-column";
    public static final String COLUMN_BLOCK = "dream-mate-column-block";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case COLUMN_BLOCK:
                return new ColumnBlockInvoker();
        }
        return null;
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
