package com.moxa.dream.module.transaction;

import java.util.Properties;

public interface TransactionFactory {
    default void setProperties(Properties properties) {

    }

    Transaction getTransaction();

}
