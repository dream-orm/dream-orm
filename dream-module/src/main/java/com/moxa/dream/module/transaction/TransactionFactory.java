package com.moxa.dream.module.transaction;

import com.moxa.dream.module.mapped.MappedStatement;

import java.util.Properties;

public interface TransactionFactory {
    default void setProperties(Properties properties) {

    }

    Transaction getTransaction(MappedStatement mappedStatement);

}
