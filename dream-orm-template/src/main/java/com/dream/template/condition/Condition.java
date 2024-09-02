package com.dream.template.condition;

import java.lang.reflect.Field;

/**
 * 条件构建
 */
public interface Condition {
    /**
     * 条件构建方法
     *
     * @param column 字段名
     * @param field  字段属性
     * @return
     */
    String getCondition(String column, Field field);
}
