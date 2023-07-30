package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.Invoker;

public interface InvokerFactory {

    /**
     * 增加自定义@函数
     *
     * @param invokers @函数
     */
    void addInvokers(Invoker... invokers);

    /**
     * 根据表名和命名空间获取对应的@函数
     *
     * @param function  @函数方法名
     * @param namespace @函数命名空间
     * @return
     */
    Invoker getInvoker(String function, String namespace);
}
