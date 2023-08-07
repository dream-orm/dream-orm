package com.dream.system.core.executor;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

import java.sql.SQLException;

/**
 * SQL执行器
 */
public interface Executor {

    /**
     * 返回SQL操作返回数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param session         SQL操作会话
     * @return
     * @throws SQLException
     */
    Object execute(MappedStatement mappedStatement, Session session) throws SQLException;

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
    void close();

    /**
     * 返回是否自动自交
     *
     * @return
     */
    boolean isAutoCommit();
}
