package com.moxa.dream.system.transaction.factory;

import com.moxa.dream.system.transaction.Transaction;

import javax.sql.DataSource;

public interface TransactionFactory {
    Transaction getTransaction(DataSource dataSource);
}
