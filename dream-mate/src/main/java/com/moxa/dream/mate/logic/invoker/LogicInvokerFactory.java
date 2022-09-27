package com.moxa.dream.mate.logic.invoker;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;

public class LogicInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "dream-mate-logic";
    public static final String LOGIC_DELETE = "dream-mate-logic-delete";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case LOGIC_DELETE:
                return new LogicInvoker();
        }
        return null;
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
