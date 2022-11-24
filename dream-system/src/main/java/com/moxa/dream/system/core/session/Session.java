package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface Session extends Closeable {

    <T> T getMapper(Class<T> type);

    Object execute(MethodInfo methodInfo, Map<String, Object> argMap);

    Object execute(MappedStatement mappedStatement);

    Object batchExecute(MethodInfo methodInfo, List<?> argList, int batchSize);

    Object batchExecute(List<MappedStatement> mappedStatementList, int batchSize);

    boolean isAutoCommit();

    void commit();

    void rollback();

    void close();

    Configuration getConfiguration();
}

