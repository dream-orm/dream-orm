package com.moxa.dream.test.antlr.invoker.call;

public class MyCallA {
    public String call(String arg1, boolean arg2, int arg3) {
        if (arg2)
            return arg1;
        else
            return String.valueOf(arg3);
    }
}
