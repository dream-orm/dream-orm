package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.smt.CustomFunctionStatement;

public interface MyFunctionFactory {
    CustomFunctionStatement create(String function);
}
