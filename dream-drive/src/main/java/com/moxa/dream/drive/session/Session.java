package com.moxa.dream.drive.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.MethodInfo;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface Session extends Closeable {

    <T> T getMapper(Class<T> type);

    Object execute(MethodInfo methodInfo, Object arg);

    int[] batch(MethodInfo methodInfo, List<?> args) throws SQLException;

    boolean isAutoCommit();

    void commit();

    void rollback();

    void close();

    Configuration getConfiguration();
}

