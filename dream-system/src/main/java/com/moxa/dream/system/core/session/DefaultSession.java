package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.*;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.mapper.DefaultMapperInvokeFactory;
import com.moxa.dream.system.mapper.MapperFactory;
import com.moxa.dream.system.mapper.MapperInvokeFactory;
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
        MappedStatement mappedStatement;
        try {
            mappedStatement = dialectFactory.compile(methodInfo, argMap);
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        return execute(mappedStatement);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) {
        Object value;
        try {
            Command command = mappedStatement.getCommand();
            switch (command) {
                case QUERY:
                    value = executor.query(mappedStatement, this);
                    break;
                case UPDATE:
                    value = executor.update(mappedStatement, this);
                    break;
                case INSERT:
                    value = executor.insert(mappedStatement, this);
                    break;
                case DELETE:
                    value = executor.delete(mappedStatement, this);
                    break;
                case TRUNCATE:
                    value = executor.truncate(mappedStatement, this);
                    break;
                case DROP:
                    value = executor.drop(mappedStatement, this);
                    break;
                case BATCH:
                    BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
                    batchMappedStatement.compile(dialectFactory);
                    List<Object> resultList = new ArrayList<>();
                    while (batchMappedStatement.hasNext()) {
                        resultList.add(executor.batch(batchMappedStatement.next(), this));
                    }
                    value = resultList;
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

    protected Object executeNone(MappedStatement mappedStatement) {
        throw new DreamRunTimeException("SQL类型" + mappedStatement.getCommand() + "不支持");
    }
}
