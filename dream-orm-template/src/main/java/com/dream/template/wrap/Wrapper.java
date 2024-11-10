package com.dream.template.wrap;

/**
 * sql操作参数修改接口
 */
public interface Wrapper {
    /**
     * 初始化参数
     *
     * @param fieldType 参数类型
     * @param arg
     */
    default void init(Class<?> fieldType, String arg) {

    }

    /**
     * 返回修改后的参数
     *
     * @param value 修改前参数
     * @return 修改后的参数
     */
    Object wrap(Object value);
}
