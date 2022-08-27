package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;

import java.util.Properties;

public interface DialectFactory {
    default void setProperties(Properties properties) {

    }

    PackageStatement compile(MethodInfo methodInfo);

    MappedStatement compile(MethodInfo methodInfo, Object arg);

    void compileAfter(MethodInfo methodInfo);

    CacheKey getCacheKey(MethodInfo methodInfo);

    DbType getDbType();


    enum DbType {
        MYSQL, MSSQL, PGSQL, ORACLE,
    }
}
