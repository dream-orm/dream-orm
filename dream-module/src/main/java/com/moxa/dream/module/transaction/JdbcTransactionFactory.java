package com.moxa.dream.module.transaction;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction() {
        return new JdbcTransaction();
    }
}
