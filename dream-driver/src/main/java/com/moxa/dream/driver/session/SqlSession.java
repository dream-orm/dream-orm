package com.moxa.dream.driver.session;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.MethodInfo;

import java.io.Closeable;

public interface SqlSession extends Closeable {

    <T> T getMapper(Class<T> type);

    Object execute(MethodInfo methodInfo, Object arg);

    boolean isAutoCommit();

    void commit();

    void rollback();

    void close();

    Configuration getConfiguration();
}

