package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;

import java.util.Properties;

public interface DialectFactory {
    default void setProperties(Properties properties) {

    }

    void setDialect(ToSQL toSQL);

    PackageStatement compile(MethodInfo methodInfo);

    MappedStatement compile(MethodInfo methodInfo, Object arg);

    CacheKey getCacheKey(MethodInfo methodInfo);

    void decoration(MethodInfo methodInfo);
}
