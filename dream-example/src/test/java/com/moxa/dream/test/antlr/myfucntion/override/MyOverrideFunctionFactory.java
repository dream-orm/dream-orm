package com.moxa.dream.test.antlr.myfucntion.override;

import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;

/**
 * isnull函数，已经被框架作为基本函数处理了，希望重新定义isnull用法
 */
public class MyOverrideFunctionFactory implements MyFunctionFactory {
    @Override
    public CustomFunctionStatement create(String function) {
        switch (function.toUpperCase()) {
            case "ISNULL":
                return new IsNullStatement();
            default:
                return null;
        }
    }
}
