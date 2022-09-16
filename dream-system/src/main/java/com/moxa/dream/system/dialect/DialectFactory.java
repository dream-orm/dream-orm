package com.moxa.dream.system.dialect;

import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapped.MethodInfo;

import java.util.Properties;

public interface DialectFactory {
    default void setProperties(Properties properties) {

    }

    MappedStatement compile(MethodInfo methodInfo, Object arg);

    DbType getDbType();
}
