package com.moxa.dream.system.dialect;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

import java.util.Properties;

public interface DialectFactory {
    default void setProperties(Properties properties) {

    }

    MappedStatement compile(MethodInfo methodInfo, Object arg);

    DbType getDbType();
}
