package com.moxa.dream.module.engine.transaction;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction() {
        return new JdbcTransaction();
    }
}
