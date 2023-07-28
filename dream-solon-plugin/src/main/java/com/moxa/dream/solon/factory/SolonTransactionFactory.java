package com.moxa.dream.solon.factory;

import com.moxa.dream.solon.transaction.SolonTransaction;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;

public class SolonTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new SolonTransaction(dataSource);
    }
}
