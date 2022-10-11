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

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
        if (methodInfo.isBatch()) {
            return executeBatch(methodInfo, argMap);
        } else {
            return executeInner(methodInfo, argMap);
        }
    }

    protected Object executeBatch(MethodInfo methodInfo, Map<String, Object> argMap) {
        if (argMap == null) {
            throw new DreamRunTimeException("批量模式，参数不能为空");
        }
        List<MappedStatement> mappedStatements = new ArrayList<>();
        if (argMap instanceof ObjectMap) {
            ObjectMap objectMap = (ObjectMap) argMap;
            Object defaultValue = objectMap.getDefaultValue();
            Map<String, Object> builtMap = objectMap.getBuiltMap();
            if (defaultValue == null) {
                throw new DreamRunTimeException("批量模式，参数类型必须是集合或数组类型，且不能为空");
            }
            if (defaultValue instanceof Collection) {
                Collection args = (Collection) defaultValue;
                for (Object o : args) {
                    mappedStatements.add(dialectFactory.compile(methodInfo, new ObjectMap(o, builtMap)));
                }
            } else if (defaultValue.getClass().isArray()) {
                int length = Array.getLength(defaultValue);
                for (int i = 0; i < length; i++) {
                    mappedStatements.add(dialectFactory.compile(methodInfo, new ObjectMap(Array.get(defaultValue, i), builtMap)));
                }
            } else {
                throw new DreamRunTimeException("批量模式，参数类型必须是集合或数组类型，而实际类型为" + defaultValue.getClass().getName());
            }
        } else {
            throw new DreamRunTimeException("批量模式，参数类型必须是" + ObjectMap.class.getName() + "，而实际类型为" + argMap.getClass().getName());
        }
        try {
            return executor.batch(mappedStatements);
        } catch (SQLException e) {
            throw new DreamRunTimeException("批量执行方法'" + methodInfo.getId() + "'失败", e);
        }
    }

    protected Object executeInner(MethodInfo methodInfo, Map<String, Object> argMap) {
        MappedStatement mappedStatement = dialectFactory.compile(methodInfo, argMap);
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
                    value = executeNone(mappedStatement);
                    break;
            }
        } catch (SQLException e) {
            throw new DreamRunTimeException("执行方法'" + methodInfo.getId() + "'失败", e);
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

    protected Object executeNone(MappedStatement mappedStatement) {
        throw new DreamRunTimeException("SQL类型" + mappedStatement.getCommand() + "不支持");
    }
}
