package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.core.action.LoopAction;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 此接口继承自LoopAction，核心操作在LoopAction提供的方法里，此类仅仅提供额外信息作为辅助
 */
public interface ActionProcessor extends LoopAction {
    void init(Field field, Map<String, Object> paramMap, Configuration configuration);
}
