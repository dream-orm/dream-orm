package com.moxa.dream.system.transaction.factory;

import com.moxa.dream.system.transaction.Transaction;

import java.util.Properties;

public interface TransactionFactory {
    default void setProperties(Properties properties) {

    }

    Transaction getTransaction();

}