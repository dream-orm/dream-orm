package com.moxa.dream.flex.function;

import com.moxa.dream.antlr.smt.Statement;

/**
 * 懒惰获取抽象树
 */
public interface LazyFunction {
    Statement getStatement();
}
