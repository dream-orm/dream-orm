package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.smt.MyFunctionStatement;

public interface MyFunctionFactory {
    MyFunctionStatement create(String function);
}
