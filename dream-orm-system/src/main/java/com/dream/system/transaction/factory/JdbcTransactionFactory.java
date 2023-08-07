package com.dream.system.transaction.factory;

import com.dream.system.transaction.JdbcTransaction;
import com.dream.system.transaction.Transaction;

import javax.sql.DataSource;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new JdbcTransaction(dataSource);
    }
}
