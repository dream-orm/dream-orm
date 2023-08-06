package com.dream.system.dialect;

import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;

/**
 * 编译工厂
 */
public interface DialectFactory {

    /**
     * 编译接口方法
     *
     * @param methodInfo 接口方法详尽信息
     * @param arg        参数
     * @return 编译后的接口方法详尽信息
     * @throws Exception
     */
    MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception;
}
