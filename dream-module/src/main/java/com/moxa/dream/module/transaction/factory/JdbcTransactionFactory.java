package com.moxa.dream.module.transaction.factory;

import com.moxa.dream.module.transaction.JdbcTransaction;
import com.moxa.dream.module.transaction.Transaction;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction() {
        return new JdbcTransaction();
    }
}
