package com.moxa.dream.template.mapper;

import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.Compile;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectMap;

import java.util.Collection;
import java.util.Map;

public class ExecuteMapper {
    private Session session;
    private ResultSetHandler resultSetHandler;

    public ExecuteMapper(Session session) {
        this(session, new SimpleResultSetHandler());
    }

    public ExecuteMapper(Session session, ResultSetHandler resultSetHandler) {
        this.session = session;
        this.resultSetHandler = resultSetHandler;
    }


    public Object execute(String sql, Object param, Class<? extends Collection> rowType, Class<?> colType) {
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
        return session.execute(methodInfo, wrapArg(wrapArg(param)));
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
