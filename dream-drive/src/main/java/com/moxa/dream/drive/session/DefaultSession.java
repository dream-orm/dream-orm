package com.moxa.dream.drive.session;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.mapper.factory.MapperFactory;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultSession implements Session {
    private final Configuration configuration;
    private final Executor executor;
    private final MapperFactory mapperFactory;
    private final DialectFactory dialectFactory;

    public DefaultSession(Configuration configuration, Executor executor) {
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
        if (methodInfo.isBatch()) {
            if (arg == null) {
                throw new DreamRunTimeException("批量模式，参数类型必须是集合或数组类型，且不能为空");
            }
            List<MappedStatement> mappedStatements = new ArrayList<>();
            if (arg instanceof Collection) {
                Collection args = (Collection) arg;
                for (Object o : args) {
                    mappedStatements.add(dialectFactory.compile(methodInfo, o));
                }
            } else if (arg.getClass().isArray()) {
                int length = Array.getLength(arg);
                for (int i = 0; i < length; i++) {
                    mappedStatements.add(dialectFactory.compile(methodInfo, Array.get(arg, i)));
                }
            } else {
                throw new DreamRunTimeException("批量模式，参数类型必须是集合或数组类型，而实际类型为" + arg.getClass().getName());
            }
            try {
                return executor.batch(mappedStatements);
            } catch (SQLException e) {
                throw new DreamRunTimeException("批量执行方法'" + methodInfo.getId() + "'失败", e);
            }
        } else {
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
                    default:
                        throw new DreamRunTimeException("SQL类型" + command + "不支持");
                }
            } catch (SQLException e) {
                throw new DreamRunTimeException("执行方法'" + methodInfo.getId() + "'失败", e);
            }
            return value;
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
}
