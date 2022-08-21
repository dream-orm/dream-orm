package com.moxa.dream.system.transaction.factory;

import com.moxa.dream.system.transaction.JdbcTransaction;
import com.moxa.dream.system.transaction.Transaction;

import javax.sql.DataSource;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new JdbcTransaction(dataSource);
    }
}
