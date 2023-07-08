package com.moxa.dream.boot.factory;

import com.moxa.dream.boot.transaction.SpringTransaction;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;

public class SpringTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new SpringTransaction(dataSource);
    }
}
