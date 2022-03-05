package com.moxa.dream.engine.executor;


import com.moxa.dream.engine.result.ResultSetHandler;
import com.moxa.dream.engine.statement.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.transaction.Transaction;

import java.sql.SQLException;

public abstract class CacheExecutor implements Executor {
    private Executor executor;

    public CacheExecutor(Executor executor) {
        this.executor = executor;
    }


    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        Object value = queryFromCache(mappedStatement);
        if (value == null) {
            value = executor.query(mappedStatement);
            if (value != null) {
                storeObject(mappedStatement, value);
            }
        }
        return value;
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        Object value = executor.update(mappedStatement);
        clearObject(mappedStatement);
        return value;
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        Object value = executor.update(mappedStatement);
        clearObject(mappedStatement);
        return value;
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        Object value = executor.update(mappedStatement);
        clearObject(mappedStatement);
        return value;
    }

    @Override
    public void commit() throws SQLException {
        executor.commit();
    }

    @Override
    public void rollback() throws SQLException {
        executor.rollback();
    }

    @Override
    public void close() throws SQLException {
        try {
            executor.close();
        } finally {
            clear();
        }
    }

    protected abstract void clear();

    protected abstract Object queryFromCache(MappedStatement mappedStatement);

    protected abstract void storeObject(MappedStatement mappedStatement, Object value);

    protected abstract void clearObject(MappedStatement mappedStatement);

    @Override
    public Transaction getTransaction(MappedStatement mappedStatement) {
        return executor.getTransaction(mappedStatement);
    }

    @Override
    public StatementHandler getStatementHandler(MappedStatement mappedStatement) {
        return executor.getStatementHandler(mappedStatement);
    }

    @Override
    public ResultSetHandler getResultSetHandler(MappedStatement mappedStatement) {
        return executor.getResultSetHandler(mappedStatement);
    }

    @Override
    public Boolean isAutoCommit() {
        return executor.isAutoCommit();
    }
}
