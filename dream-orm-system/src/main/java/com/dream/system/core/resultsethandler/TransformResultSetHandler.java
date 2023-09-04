package com.dream.system.core.resultsethandler;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public final class TransformResultSetHandler<T, R> implements ResultSetHandler {
    private Function<T, R> fn;

    public TransformResultSetHandler(Function<T, R> fn) {
        this.fn = fn;
    }

    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement, Session session) throws SQLException {
        ResultSetHandler resultSetHandler = mappedStatement.getConfiguration().getResultSetHandler();
        if (resultSetHandler == this) {
            throw new DreamRunTimeException("默认映射器不能是'" + this.getClass().getName() + "'");
        }
        Object result = resultSetHandler.result(resultSet, mappedStatement, session);
        return fn.apply((T) result);
    }
}
