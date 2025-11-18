package com.dream.system.core.executor;


import com.dream.system.cache.CacheKey;
import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.Command;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCacheExecutor implements Executor {
    protected Executor nextExecutor;
    protected Set<CacheKey> nullSet = new HashSet<>();

    public AbstractCacheExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object execute(MappedStatement mappedStatement, Session session) throws SQLException {
        Command command = mappedStatement.getCommand();
        if (Command.QUERY == command) {
            return query(mappedStatement, session);
        } else {
            return update(mappedStatement, session);
        }
    }

    protected Object query(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result;
        if (cache(mappedStatement)) {
            result = queryFromCache(mappedStatement);
            if (result == null && !nullSet.contains(mappedStatement.getUniqueKey())) {
                result = nextExecutor.execute(mappedStatement, session);
                if (result != null) {
                    storeObject(mappedStatement, result);
                } else {
                    nullSet.add(mappedStatement.getUniqueKey());
                }
            }
        } else {
            result = nextExecutor.execute(mappedStatement, session);
        }
        return result;
    }

    protected Object update(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result = nextExecutor.execute(mappedStatement, session);
        clearObject(mappedStatement);
        return result;
    }

    protected Object batch(BatchMappedStatement batchMappedStatement, Session session) throws SQLException {
        Object result = nextExecutor.execute(batchMappedStatement, session);
        clearObject(batchMappedStatement);
        return result;
    }

    @Override
    public boolean isAutoCommit() {
        return nextExecutor.isAutoCommit();
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
        nullSet.clear();
    }

    protected abstract boolean cache(MappedStatement mappedStatement);

    protected abstract Object queryFromCache(MappedStatement mappedStatement);

    protected abstract void storeObject(MappedStatement mappedStatement, Object value);

    protected abstract void clearObject(MappedStatement mappedStatement);
}
