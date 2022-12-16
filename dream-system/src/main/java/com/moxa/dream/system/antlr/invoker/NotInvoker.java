package com.moxa.dream.system.antlr.invoker;


public class NotInvoker extends NonInvoker {
    public static final String FUNCTION = "not";

    @Override
    public boolean isEmpty(Object value) {
        return super.isEmpty(value) || "".equals(value.toString());
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
