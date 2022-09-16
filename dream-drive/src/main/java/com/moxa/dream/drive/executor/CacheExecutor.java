package com.moxa.dream.drive.executor;


import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class CacheExecutor implements Executor {
    private final Executor executor;

    public CacheExecutor(Executor executor) {
        this.executor = executor;
    }


    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        Object value;
        if (mappedStatement.isCache()) {
            value = queryFromCache(mappedStatement);
            if (value == null) {
                value = executor.query(mappedStatement);
                if (value != null) {
                    storeObject(mappedStatement, value);
                }
            }
        } else {
            value = executor.query(mappedStatement);
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
        Object value = executor.insert(mappedStatement);
        clearObject(mappedStatement);
        return value;
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        Object value = executor.delete(mappedStatement);
        clearObject(mappedStatement);
        return value;
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements) throws SQLException {
        Object value = executor.batch(mappedStatements);
        clearObject(mappedStatements.get(0));
        return value;
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
        clear();
    }

    @Override
    public Statement getStatement() {
        return executor.getStatement();
    }

    protected abstract void clear();

    protected abstract Object queryFromCache(MappedStatement mappedStatement);

    protected abstract void storeObject(MappedStatement mappedStatement, Object value);

    protected abstract void clearObject(MappedStatement mappedStatement);

    @Override
    public boolean isAutoCommit() {
        return executor.isAutoCommit();
    }
}