package com.dream.system.core.session;

import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.executor.Executor;
import com.dream.system.dialect.DialectFactory;
import com.dream.system.mapper.DefaultMapperInvokeFactory;
import com.dream.system.mapper.MapperFactory;
import com.dream.system.mapper.MapperInvokeFactory;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.SQLException;
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
            value = executor.execute(mappedStatement, this);
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
}
