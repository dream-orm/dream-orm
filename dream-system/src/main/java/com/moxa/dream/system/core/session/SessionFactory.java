package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;

/**
 * SQL操作会话创建工厂
 */
public interface SessionFactory {
    /**
     * 开启SQL操作会话
     *
     * @param autoCommit 自动提交标识
     * @return
     */
    Session openSession(boolean autoCommit);

    /**
     * 开启SQL操作会话
     *
     * @param executor 执行器
     * @return
     */
    Session openSession(Executor executor);

    /**
     * 返回配置
     *
     * @return
     */
    Configuration getConfiguration();
}
