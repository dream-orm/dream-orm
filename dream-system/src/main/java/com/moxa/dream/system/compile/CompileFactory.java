package com.moxa.dream.system.compile;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;

public interface CompileFactory {

    PackageStatement compile(String sql) throws Exception;

    CacheKey uniqueKey(String sql);
}
