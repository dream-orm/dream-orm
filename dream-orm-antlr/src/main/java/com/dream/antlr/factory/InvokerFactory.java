package com.dream.antlr.factory;

import com.dream.antlr.invoker.Invoker;

import java.util.function.Supplier;

public interface InvokerFactory {

    /**
     * 增加自定义@函数
     *
     * @param function @函数方法名
     * @param supplier 生成invoker
     */
    void addInvoker(String function, Supplier<Invoker> supplier);

    /**
     * 根据函数方法名获取对应的@函数
     *
     * @param function @函数方法名
     * @return
     */
    Invoker getInvoker(String function);
}
