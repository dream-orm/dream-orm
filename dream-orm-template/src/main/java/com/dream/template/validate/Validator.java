package com.dream.template.validate;


import com.dream.system.config.Command;
import com.dream.system.core.session.Session;

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
     * 校验参数，不通过则返回非空字符串即可
     *
     * @param value    参数值
     * @param paramMap 自定义参数
     * @return 错误信息
     */
    String validate(T value, Map<String, Object> paramMap);
}
