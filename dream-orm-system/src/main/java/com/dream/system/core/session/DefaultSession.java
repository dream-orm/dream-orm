package com.dream.system.core.session;

import com.dream.system.action.ActionProvider;
import com.dream.system.cache.CacheKey;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.executor.Executor;
import com.dream.system.dialect.DialectFactory;
import com.dream.system.mapper.MapperFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectMap;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class DefaultSession implements Session {
    protected final Configuration configuration;
    protected Executor executor;
    protected MapperFactory mapperFactory;
    protected DialectFactory dialectFactory;
    private final Map<String, MethodInfo> methodInfoMap;

    public DefaultSession(Configuration configuration, Executor executor) {
        this(configuration, executor, new WeakHashMap<>());
    }

    public DefaultSession(Configuration configuration, Executor executor, Map<String, MethodInfo> methodInfoMap) {
        this.configuration = configuration;
        this.executor = executor;
        this.mapperFactory = configuration.getMapperFactory();
        this.dialectFactory = configuration.getDialectFactory();
        this.methodInfoMap = methodInfoMap;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperFactory.getMapper(type, this);
    }

    @Override
    public Object execute(ActionProvider actionProvider, Object arg) {
        String sql = actionProvider.sql();
        Class<? extends Collection> rowType = actionProvider.rowType();
        Class<?> colType = actionProvider.colType();
        if (rowType == null || colType == null) {
            throw new DreamRunTimeException("返回的类型不能为空");
        }
        CacheKey cacheKey = SystemUtil.cacheKey(sql, 5);
        cacheKey.update(rowType.getName(), colType.getName(), actionProvider.page());
        String id = cacheKey.toString();
        MethodInfo methodInfo = methodInfoMap.get(id);
        if (methodInfo == null) {
            synchronized (methodInfoMap) {
                methodInfo = methodInfoMap.get(id);
                if (methodInfo == null) {
                    methodInfo = new MethodInfo()
                            .setConfiguration(configuration)
                            .setId(id)
                            .setRowType(rowType)
                            .setColType(colType)
                            .setSql(sql)
                            .setPage(actionProvider.page())
                            .setStatementHandler(actionProvider.statementHandler())
                            .setResultSetHandler(actionProvider.resultSetHandler());
                    Integer timeOut = actionProvider.timeOut();
                    if (timeOut != null) {
                        methodInfo.setTimeOut(timeOut);
                    }
                    InitAction initAction = actionProvider.initAction();
                    if (initAction != null) {
                        methodInfo.addInitAction(initAction);
                    }
                    LoopAction loopAction = actionProvider.loopAction();
                    if (loopAction != null) {
                        methodInfo.addLoopAction(loopAction);
                    }
                    DestroyAction destroyAction = actionProvider.destroyAction();
                    if (destroyAction != null) {
                        methodInfo.addDestroyAction(destroyAction);
                    }
                    methodInfoMap.put(id, methodInfo);
                }
            }
        }
        if (arg != null) {
            if (arg instanceof Map) {
                return execute(methodInfo, (Map) arg);
            } else {
                return execute(methodInfo, new ObjectMap(arg));
            }
        }
        return execute(methodInfo, new HashMap<>());
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
            throw new DreamRunTimeException("执行'" + mappedStatement.getId() + "'失败\n原因:" + e.getMessage() + "\nsql:" + mappedStatement.getSql(), e);
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
