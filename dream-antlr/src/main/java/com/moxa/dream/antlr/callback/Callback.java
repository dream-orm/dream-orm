package com.moxa.dream.antlr.callback;

import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;

public interface Callback {

    default void init(ToAssist assist) {

    }

    Statement call(String className, String methodName, String[] params, Object[] args);


}
