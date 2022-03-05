package com.moxa.dream.module.transaction;

import com.moxa.dream.module.mapped.MappedStatement;

public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(MappedStatement mappedStatement) {
        return new JdbcTransaction();
    }
}
