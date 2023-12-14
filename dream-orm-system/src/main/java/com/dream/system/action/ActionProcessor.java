package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.core.action.LoopAction;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 此接口继承自LoopAction，具备查询结果遍历功能
 */
public interface ActionProcessor extends LoopAction {
    /**
     * 提供额外信息参数，加强版loopAction
     *
     * @param field         注解修饰的对象属性
     * @param paramMap      注解的内容
     * @param configuration 全局配置
     */
    void init(Field field, Map<String, Object> paramMap, Configuration configuration);
}
