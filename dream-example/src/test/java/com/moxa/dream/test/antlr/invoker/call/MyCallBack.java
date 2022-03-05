package com.moxa.dream.test.antlr.invoker.call;

import com.moxa.dream.antlr.callback.AbstractCallback;

public class MyCallBack extends AbstractCallback {
    MyCallA myCallA = new MyCallA();

    @Override
    protected String calling(String className, String methodName, String[] params, Object[] args) {
        switch (methodName) {
            case "callA":
                return myCallA.call((String) args[0], (boolean) args[1], (int) args[2]);

        }
        return null;
    }
}