package com.dream.system.core.executor;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.listener.factory.ListenerFactory;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectUtil;

import java.sql.SQLException;

public class ListenerExecutor implements Executor {
    protected ListenerFactory listenerFactory;
    protected Executor nextExecutor;

    public ListenerExecutor(Executor nextExecutor, ListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object execute(MappedStatement mappedStatement, Session session) throws SQLException {
        Listener[] listeners = null;
        if (listenerFactory != null) {
            listeners = listenerFactory.getListeners();
        }
        if (!ObjectUtil.isNull(listeners)) {
            beforeListeners(listeners, mappedStatement, session);
            Object result;
            try {
                result = nextExecutor.execute(mappedStatement, session);
            } catch (Throwable e) {
                exceptionListeners(listeners, e, mappedStatement, session);
                throw e;
            }
            afterReturnListeners(listeners, result, mappedStatement, session);
            return result;
        } else {
            return nextExecutor.execute(mappedStatement, session);
        }
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
    }

    protected void beforeListeners(Listener[] listeners, MappedStatement mappedStatement, Session session) {
        for (Listener listener : listeners) {
            listener.before(mappedStatement, session);
        }
    }

    protected void afterReturnListeners(Listener[] listeners, Object result, MappedStatement mappedStatement, Session session) {
        for (Listener listener : listeners) {
            listener.afterReturn(result, mappedStatement, session);
        }
    }

    protected void exceptionListeners(Listener[] listeners, Throwable e, MappedStatement mappedStatement, Session session) {
        for (Listener listener : listeners) {
            listener.exception(e, mappedStatement, session);
        }
    }

}
