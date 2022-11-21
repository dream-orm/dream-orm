package com.moxa.dream.system.core.session;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.mapper.DefaultMapperInvokeFactory;
import com.moxa.dream.system.mapper.MapperFactory;
import com.moxa.dream.system.mapper.MapperInvokeFactory;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultSession implements Session {
    protected Configuration configuration;
    protected Executor executor;
    protected MapperFactory mapperFactory;
    protected DialectFactory dialectFactory;
    protected MapperInvokeFactory mapperInvokeFactory;

    public DefaultSession(Configuration configuration, Executor executor) {
        this(configuration, executor, new DefaultMapperInvokeFactory());
    }

    public DefaultSession(Configuration configuration, Executor executor, MapperInvokeFactory mapperInvokeFactory) {
        this.configuration = configuration;
        this.executor = executor;
        this.mapperFactory = configuration.getMapperFactory();
        this.dialectFactory = configuration.getDialectFactory();
        this.mapperInvokeFactory = mapperInvokeFactory;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperFactory.getMapper(type, mapperInvokeFactory.getMapperInvoke(this));
    }

    @Override
    public Object execute(MethodInfo methodInfo, Map<String, Object> argMap) {
        MappedStatement mappedStatement = dialectFactory.compile(methodInfo, argMap);
        return execute(mappedStatement);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) {
        Object value;
        try {
            Command command = getCommand(mappedStatement);
            switch (command) {
                case QUERY:
                    value = executor.query(mappedStatement);
                    break;
                case UPDATE:
                    value = executor.update(mappedStatement);
                    break;
                case INSERT:
                    value = executor.insert(mappedStatement);
                    break;
                case DELETE:
                    value = executor.delete(mappedStatement);
                    break;
                default:
                    value = executeNone(mappedStatement);
                    break;
            }
        } catch (SQLException e) {
            throw new DreamRunTimeException("执行'" + mappedStatement.getId() + "'失败：" + e.getMessage(), e);
        }
        return value;
    }

    @Override
    public Object batchExecute(MethodInfo methodInfo, List<?> argList, int batchSize) {
        if (argList == null || argList.isEmpty()) {
            return null;
        }
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
        for (Object arg : argList) {
            if (arg == null) {
                continue;
            }
            Map<String, Object> argMap;
            if (arg instanceof Map) {
                argMap = (Map<String, Object>) arg;
            } else {
                argMap = new ObjectMap(arg);
            }
            mappedStatementList.add(dialectFactory.compile(methodInfo, argMap));
        }
        return batchExecute(mappedStatementList, batchSize);
    }

    @Override
    public Object batchExecute(List<MappedStatement> mappedStatementList, int batchSize) {
        if (mappedStatementList == null || mappedStatementList.isEmpty()) {
            return null;
        }
        try {
            if (batchSize > 0) {
                int size = mappedStatementList.size();
                int oldSize = 0;
                while (true) {
                    int newSize = oldSize + batchSize;
                    if (newSize >= size) {
                        executor.batch(mappedStatementList.subList(oldSize, size));
                        break;
                    } else {
                        executor.batch(mappedStatementList.subList(oldSize, newSize));
                        oldSize = newSize;
                    }
                }
            } else {
                executor.batch(mappedStatementList);
            }
            return null;
        } catch (SQLException e) {
            throw new DreamRunTimeException("批量执行失败：" + e.getMessage(), e);
        }
    }

    @Override
    public boolean isAutoCommit() {
        return executor.isAutoCommit();
    }


    @Override
    public void commit() {
        executor.commit();

    }

    @Override
    public void rollback() {
        executor.rollback();
    }

    @Override
    public void close() {
        executor.close();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    protected Command getCommand(MappedStatement mappedStatement) {
        return mappedStatement.getCommand();
    }

    protected Object executeNone(MappedStatement mappedStatement) {
        throw new DreamRunTimeException("SQL类型" + mappedStatement.getCommand() + "不支持");
    }
}
