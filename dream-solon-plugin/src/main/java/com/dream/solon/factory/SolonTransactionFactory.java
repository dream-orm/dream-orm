package com.dream.solon.factory;

import com.dream.solon.transaction.SolonTransaction;
import com.dream.system.transaction.Transaction;
import com.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;

public class SolonTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new SolonTransaction(dataSource);
    }
}
