package com.dream.system.core.listener;

import com.dream.system.config.MappedStatement;

import java.sql.SQLException;

/**
 * SQL执行监听器
 */
public interface Listener {

    /**
     * SQL操作执行前进入此方法
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void before(MappedStatement mappedStatement);

    /**
     * SQL操作正常返回进入此方法
     *
     * @param result          SQL操作返回的数据
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void afterReturn(Object result, MappedStatement mappedStatement);

    /**
     * SQL操作异常进入此方法
     *
     * @param e               异常类
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void exception(SQLException e, MappedStatement mappedStatement);
}
