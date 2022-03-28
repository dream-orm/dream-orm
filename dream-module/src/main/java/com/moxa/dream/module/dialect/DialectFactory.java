package com.moxa.dream.module.dialect;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.MethodInfo;

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
