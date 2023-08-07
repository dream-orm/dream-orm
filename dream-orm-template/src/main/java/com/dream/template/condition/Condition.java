package com.dream.template.condition;

/**
 * 条件构建
 */
public interface Condition {
    /**
     * 条件构建方法
     *
     * @param table  表名
     * @param column 字段名
     * @param field  字段属性
     * @return
     */
    String getCondition(String table, String column, String field);
}
