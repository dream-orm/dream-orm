package com.dream.system.transaction.factory;

import com.dream.system.transaction.Transaction;

import javax.sql.DataSource;

/**
 * 事务管理器工厂
 */
public interface TransactionFactory {
    /**
     * 根据数据源常见事务管理器
     *
     * @param dataSource 数据源
     * @return
     */
    Transaction getTransaction(DataSource dataSource);
}
