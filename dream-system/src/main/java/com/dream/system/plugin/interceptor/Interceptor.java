package com.dream.system.plugin.interceptor;

import com.dream.system.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 接口方法代理选择器
 */
public interface Interceptor {
    /**
     * 自定义接口代理处理逻辑
     *
     * @param invocation 接口代理信息
     * @return
     * @throws Throwable
     */
    Object interceptor(Invocation invocation) throws Throwable;

    /**
     * 代理的方法
     *
     * @return 代理的方法
     * @throws Exception
     */
    Set<Method> methods() throws Exception;
}
