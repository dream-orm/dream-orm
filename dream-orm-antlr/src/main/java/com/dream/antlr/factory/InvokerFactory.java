package com.dream.antlr.factory;

import com.dream.antlr.invoker.Invoker;

public interface InvokerFactory {

    /**
     * 增加自定义@函数
     *
     * @param invokers @函数
     */
    void addInvokers(Invoker... invokers);

    /**
     * 根据函数方法名获取对应的@函数
     *
     * @param functionName @函数方法名
     * @return
     */
    Invoker getInvoker(String functionName);
}
