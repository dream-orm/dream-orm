package com.dream.antlr.factory;

import com.dream.antlr.smt.MyFunctionStatement;

public interface MyFunctionFactory {
    /**
     * 根据方法名创建自定义的抽象树
     *
     * @param function 方法名
     * @return 自定义的抽象树
     */
    MyFunctionStatement create(String function);
}
