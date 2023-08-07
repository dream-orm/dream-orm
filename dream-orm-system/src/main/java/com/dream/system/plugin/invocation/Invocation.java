package com.dream.system.plugin.invocation;

import java.lang.reflect.Method;

/**
 * 接口代理信息
 */
public interface Invocation {
    /**
     * 执行下一步
     *
     * @return
     * @throws Throwable
     */
    Object proceed() throws Throwable;

    /**
     * 不代理的原始对象
     *
     * @return
     */
    Object getTarget();

    /**
     * 代理的方法
     *
     * @return
     */
    Method getMethod();

    /**
     * 代理方法的参数
     *
     * @return
     */
    Object[] getArgs();
}
