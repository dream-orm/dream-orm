package com.moxa.dream.template.mapper;

import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.Compile;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

public class ExecuteMapper {
    private Session session;
    private ResultSetHandler resultSetHandler;
    private DialectFactory dialectFactory;

    public ExecuteMapper(Session session) {
        this(session, new SimpleResultSetHandler());
    }

    public ExecuteMapper(Session session, ResultSetHandler resultSetHandler) {
        this.session = session;
        this.resultSetHandler = resultSetHandler;
        this.dialectFactory = session.getConfiguration().getDialectFactory();
    }


    public Object execute(String sql, Object arg, Class<? extends Collection> rowType, Class<?> colType, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(new Object[]{sql, rowType, colType});
        MethodInfo methodInfo = new MethodInfo()
                .setConfiguration(session.getConfiguration())
                .setCompile(Compile.UN_ANTLR_COMPILE)
                .setResultSetHandler(resultSetHandler)
                .setMethodKey(cacheKey)
                .setSql(sql)
                .setRowType(rowType)
                .setColType(colType);
        if (methodInfoConsumer != null) {
            methodInfoConsumer.accept(methodInfo);
        }
        return execute(methodInfo, arg, mappedStatementConsumer);
    }

    protected Object execute(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
        MappedStatement mappedStatement;
        try {
            mappedStatement = dialectFactory.compile(methodInfo, wrapArg(arg));
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        return execute(mappedStatement, mappedStatementConsumer);
    }

    protected Object execute(MappedStatement mappedStatement, Consumer<MappedStatement> mappedStatementConsumer) {
        if (mappedStatementConsumer != null) {
            mappedStatementConsumer.accept(mappedStatement);
        }
        return session.execute(mappedStatement);
    }

    protected Map<String, Object> wrapArg(Object arg) {
        if (arg != null) {
            if (arg instanceof Map) {
                return (Map<String, Object>) arg;
            } else {
                return new ObjectMap(arg);
            }
        } else {
            return null;
        }
    }
}
