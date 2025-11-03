package com.dream.antlr.factory;

import com.dream.antlr.smt.DefaultMyFunctionStatement;
import com.dream.antlr.smt.MyFunctionStatement;

public class DefaultMyFunctionFactory implements MyFunctionFactory {
    @Override
    public MyFunctionStatement create(String function) {
        return new DefaultMyFunctionStatement(function);
    }
}
