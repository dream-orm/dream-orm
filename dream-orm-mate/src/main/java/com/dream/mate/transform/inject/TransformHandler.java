package com.dream.mate.transform.inject;

import com.dream.antlr.invoker.Invoker;

import java.util.List;


public interface TransformHandler {

    /**
     * 关键字拦截
     *
     * @param column      字段名
     * @param invokerList 当前应用的@函数解析器
     * @return 是否是关键字
     */
    boolean intercept(String column, List<Invoker> invokerList);
}
