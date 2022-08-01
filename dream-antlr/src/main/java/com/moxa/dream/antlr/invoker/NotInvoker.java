package com.moxa.dream.antlr.invoker;


public class NotInvoker extends NonInvoker{
    @Override
    public boolean isEmpty(Object value) {
        return super.isEmpty(value)||"".equals(value.toString());
    }
}
