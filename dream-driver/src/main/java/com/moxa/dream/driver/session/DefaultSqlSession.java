package com.moxa.dream.driver.session;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.mapper.factory.MapperFactory;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private final Executor executor;
    private final MapperFactory mapperFactory;
    private final DialectFactory dialectFactory;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
        this.mapperFactory = configuration.getMapperFactory();
        this.dialectFactory = configuration.getDialectFactory();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperFactory.getMapper(type, (methodInfo, arg)
                -> execute(methodInfo, arg)
        );
    }

    @Override
    public Object execute(MethodInfo methodInfo, Object arg) {
        MappedStatement mappedStatement = dialectFactory.compile(methodInfo, arg);
        Object value = null;
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
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    protected Command getCommand(MappedStatement mappedStatement) {
        return mappedStatement.getCommand();
    }
}
