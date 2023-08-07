package com.dream.boot.factory;

import com.dream.boot.transaction.SpringTransaction;
import com.dream.system.transaction.Transaction;
import com.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;

public class SpringTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new SpringTransaction(dataSource);
    }
}
