package com.moxa.dream.test.myfucntion.override;

import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.smt.MyFunctionStatement;

/**
 * isnull函数，已经被框架作为基本函数处理了，希望重新定义isnull用法
 */
public class MyOverrideFunctionFactory implements MyFunctionFactory {
    @Override
    public MyFunctionStatement create(String function) {
        switch (function.toUpperCase()) {
            case "ISNULL":
                return new IsNullStatement();
            default:
                return null;
        }
    }
}
