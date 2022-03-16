package com.moxa.dream.antlr.callback;

import com.moxa.dream.antlr.smt.Statement;

public interface Callback {

    Statement call(String className, String methodName, String[] params, Object[] args);
}
