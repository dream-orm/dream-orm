package com.moxa.dream.driver.session;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.engine.executor.Executor;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.MapperFactory;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;
    private MapperFactory mapperFactory;
    private DialectFactory dialectFactory;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        ObjectUtil.requireNonNull(configuration, "Property 'configuration' is required");
        ObjectUtil.requireNonNull(executor, "Property 'executor' is required");
        this.configuration = configuration;
        this.executor = executor;
        this.mapperFactory = configuration.getMapperFactory();
        this.dialectFactory = configuration.getDialectFactory();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperFactory.getMapper(type, (methodInfo, arg, args)
                -> execute(methodInfo, arg)
        );
    }

    @Override
    public Object execute(MethodInfo methodInfo, Object arg) {
        MappedStatement mappedStatement = dialectFactory.compile(methodInfo, arg);
        Object value = null;
        try {
            Command command = mappedStatement.getCommand();
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
        } catch (SQLException e) {
            throw new DriverException(e);
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
