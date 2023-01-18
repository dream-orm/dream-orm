package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

import java.io.Closeable;
import java.util.Map;

public interface Session extends Closeable {

    <T> T getMapper(Class<T> type);

    Object execute(MethodInfo methodInfo, Map<String, Object> argMap);

    Object execute(MappedStatement mappedStatement);

    boolean isAutoCommit();

    void commit();

    void rollback();

    @Override
    void close();

    Configuration getConfiguration();
}

