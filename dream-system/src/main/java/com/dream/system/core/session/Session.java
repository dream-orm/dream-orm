package com.dream.system.core.session;

import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;

import java.io.Closeable;
import java.util.Map;

public interface Session extends Closeable {

    /**
     * 根据接口类型获取代理的对象
     *
     * @param type 接口
     * @param <T>  代理的对象
     * @return
     */
    <T> T getMapper(Class<T> type);

    /**
     * 执行SQL操作
     *
     * @param methodInfo 接口方法详尽信息
     * @param argMap     参数
     * @return
     */
    Object execute(MethodInfo methodInfo, Map<String, Object> argMap);

    /**
     * 执行SQL操作
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return
     */
    Object execute(MappedStatement mappedStatement);

    /**
     * 返回是否自动提交
     *
     * @return
     */
    boolean isAutoCommit();

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();

    /**
     * 关闭
     */
    @Override
    void close();

    /**
     * 返回配置
     *
     * @return
     */
    Configuration getConfiguration();
}

