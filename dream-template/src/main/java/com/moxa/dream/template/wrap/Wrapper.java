package com.moxa.dream.template.wrap;

/**
 * sql操作参数修改接口
 */
public interface Wrapper {
    /**
     * 返回修改后的参数
     *
     * @param value 修改前参数
     * @return
     */
    Object wrap(Object value);
}
