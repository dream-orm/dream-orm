package com.moxa.dream.test.myfucntion.senior;

import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.smt.MyFunctionStatement;

/**
 * 函数参数的解析格式是采用默认进行解析的，只能解析使用逗号组合成的参数，但一定会存在不能解析的情形，请阅读ExtractStatement.java
 */
public class MySeniorFunctionFactory implements MyFunctionFactory {

    @Override
    public MyFunctionStatement create(String function) {
        switch (function.toUpperCase()) {
            case "EXTRACT":
                return new ExtractStatement();
            default:
                return null;
        }
    }

}
