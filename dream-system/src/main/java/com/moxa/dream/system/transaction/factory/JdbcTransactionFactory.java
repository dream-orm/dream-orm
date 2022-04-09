package com.moxa.dream.system.transaction.factory;

import com.moxa.dream.system.transaction.JdbcTransaction;
import com.moxa.dream.system.transaction.Transaction;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction() {
        return new JdbcTransaction();
    }
}
