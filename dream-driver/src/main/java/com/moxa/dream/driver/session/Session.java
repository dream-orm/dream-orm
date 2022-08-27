package com.moxa.dream.driver.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.MethodInfo;

import java.io.Closeable;

public interface Session extends Closeable {

    <T> T getMapper(Class<T> type);

    Object execute(MethodInfo methodInfo, Object arg);

    boolean isAutoCommit();

    void commit();

    void rollback();

    void close();

    Configuration getConfiguration();
}

