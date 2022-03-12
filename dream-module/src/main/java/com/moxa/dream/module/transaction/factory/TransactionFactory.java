package com.moxa.dream.module.transaction.factory;

import com.moxa.dream.module.transaction.Transaction;

import java.util.Properties;

public interface TransactionFactory {
    default void setProperties(Properties properties) {

    }

    Transaction getTransaction();

}
