package com.moxa.dream.system.core.executor;


import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCacheExecutor implements Executor {
    protected Executor nextExecutor;

    public AbstractCacheExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }


    @Override
    public Object query(MappedStatement mappedStatement, Session session) throws SQLException {
        Object value;
        if (mappedStatement.isCache()) {
            value = queryFromCache(mappedStatement);
            if (value == null) {
                value = nextExecutor.query(mappedStatement, session);
                if (value != null) {
                    storeObject(mappedStatement, value);
                }
            }
        } else {
            value = nextExecutor.query(mappedStatement, session);
        }
        return value;
    }

    @Override
    public Object update(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result = nextExecutor.update(mappedStatement, session);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object insert(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result = nextExecutor.insert(mappedStatement, session);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object delete(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result = nextExecutor.delete(mappedStatement, session);
        clearObject(mappedStatement);
        return result;
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements, Session session) throws SQLException {
        Object result = nextExecutor.batch(mappedStatements, session);
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

    public abstract void clear();

    protected abstract Object queryFromCache(MappedStatement mappedStatement);

    protected abstract void storeObject(MappedStatement mappedStatement, Object value);

    protected abstract void clearObject(MappedStatement mappedStatement);

    @Override
    public boolean isAutoCommit() {
        return nextExecutor.isAutoCommit();
    }
}
