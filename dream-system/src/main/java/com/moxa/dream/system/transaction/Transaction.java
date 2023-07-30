package com.moxa.dream.system.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务处理类
 */
public interface Transaction {

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

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
     * 返回是否自动提交
     *
     * @return
     */
    boolean isAutoCommit();

    /**
     * 设置是否自动提交
     *
     * @param autoCommit 自动提交标识
     */
    void setAutoCommit(boolean autoCommit);
}
