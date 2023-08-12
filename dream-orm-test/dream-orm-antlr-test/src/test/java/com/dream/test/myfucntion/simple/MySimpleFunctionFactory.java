package com.dream.test.myfucntion.simple;

import com.dream.antlr.factory.DefaultMyFunctionFactory;
import com.dream.antlr.smt.MyFunctionStatement;

/**
 * 翻译框架不可能把每一个函数都支持，因此为了满足开发者需求，必须提供可翻译未知函数的方案，提供了一个接口，完成自定义函数
 * 如果不是自定义函数，必须返回空，因为框架优先创建自定义函数，如果为空，再决定是否是基本函数，这样的好处，开发者可以重写框架
 * 提供的基本函数
 */
public class MySimpleFunctionFactory extends DefaultMyFunctionFactory {
    @Override
    public MyFunctionStatement create(String function) {
        //假定使用decode函数，但未在基础函数中实现，却又想使用
        switch (function.toUpperCase()) {
            case "DECODE":
                return new DecodeStatement();
            default:
                return super.create(function);
        }
    }
}
