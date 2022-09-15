package com.moxa.dream.system.compile;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;

import java.util.Properties;

public interface CompileFactory {
    default void setProperties(Properties properties) {

    }

    PackageStatement compile(String sql);

    CacheKey uniqueKey(String sql);
}
