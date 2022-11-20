package com.moxa.dream.system.core.executor;


import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.SessionFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractCacheExecutor implements Executor {
    protected Executor nextExecutor;

    public AbstractCacheExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }


    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        Object value;
        if (mappedStatement.isCache()) {
            value = queryFromCache(mappedStatement);
            if (value == null) {
                value = nextExecutor.query(mappedStatement);
                if (value != null) {
                    storeObject(mappedStatement, value);
                }
            }
        } else {
            value = nextExecutor.query(mappedStatement);
        }
        return value;
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        Object result = nextExecutor.update(mappedStatement);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        Object result = nextExecutor.insert(mappedStatement);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        Object result = nextExecutor.delete(mappedStatement);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements) throws SQLException {
        Object result = nextExecutor.batch(mappedStatements);
        clearObject(mappedStatements.get(0));
        return result;
    }

    @Override
    public void commit() {
        nextExecutor.commit();
    }

    @Override
    public void rollback() {
        nextExecutor.rollback();
    }

    @Override
    public void close() {
        nextExecutor.close();
    }

    @Override
    public Statement getStatement() {
        return nextExecutor.getStatement();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return nextExecutor.getSessionFactory();
    }

    public abstract void clear();

    protected abstract Object queryFromCache(MappedStatement mappedStatement);

    protected abstract void storeObject(MappedStatement mappedStatement, Object value);

    protected abstract void clearObject(MappedStatement mappedStatement);

    @Override
    public boolean isAutoCommit() {
        return nextExecutor.isAutoCommit();
    }
}
