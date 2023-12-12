package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.core.action.LoopAction;

import java.lang.reflect.Field;
import java.util.Map;

public interface ActionProcessor extends LoopAction {
    void init(Field field, Map<String, Object> paramMap, Configuration configuration);
}
