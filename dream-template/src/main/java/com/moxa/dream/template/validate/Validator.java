package com.moxa.dream.template.validate;


import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.core.session.Session;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 参数校验器
 *
 * @param <T>
 */
public interface Validator<T> {
    /**
     * 是否进行参数校验
     *
     * @param session SQL操作会话
     * @param type    对象类型
     * @param field   对象字段属性
     * @param command 执行SQL类型
     * @return
     */
    default boolean isValid(Session session, Class type, Field field, Command command) {
        return true;
    }

    /**
     * 校验参数，不通过自定义异常即可
     *
     * @param value    参数值
     * @param paramMap 自定义参数
     */
    void validate(T value, Map<String, Object> paramMap);
}
