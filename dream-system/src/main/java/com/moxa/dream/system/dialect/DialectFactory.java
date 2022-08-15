package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;

public interface DialectFactory {
    PackageStatement compile(MethodInfo methodInfo);

    MappedStatement compile(MethodInfo methodInfo, Object arg);

    CacheKey getCacheKey(MethodInfo methodInfo);

    void wrapper(MethodInfo methodInfo);

    DbType getDbType();

    enum DbType {
        MYSQL, MSSQL, PGSQL, ORACLE,
    }
}
