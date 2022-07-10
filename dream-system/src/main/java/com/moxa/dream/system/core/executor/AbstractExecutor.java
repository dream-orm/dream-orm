package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executorhandler.*;
import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;

public abstract class AbstractExecutor implements Executor {
    protected Transaction transaction;
    protected Configuration configuration;
    protected StatementHandler statementHandler;
    protected ResultSetHandler resultSetHandler;
    protected ListenerFactory listenerFactory;

    public AbstractExecutor(Configuration configuration, boolean autoCommit) {
        this.configuration = configuration;
        this.transaction = configuration.getTransaction(autoCommit);
        this.listenerFactory = configuration.getListenerFactory();
        this.statementHandler = createStatementHandler();
        this.resultSetHandler = createResultSetHandler();
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        QueryListener[] queryListeners = null;
        if (listenerFactory != null) {
            queryListeners = listenerFactory.getQueryListener();
        }
        return doExecutor(queryListeners, mappedStatement, new QueryExecutorHandler(statementHandler, resultSetHandler, transaction.getConnection()));
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        UpdateListener[] updateListeners = null;
        if (listenerFactory != null) {
            updateListeners = listenerFactory.getUpdateListener();
        }
        return doExecutor(updateListeners, mappedStatement, new UpdateExecutorHandler(statementHandler, transaction.getConnection()));
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        InsertListener[] insertListeners = null;
        if (listenerFactory != null) {
            insertListeners = listenerFactory.getInsertListener();
        }
        return doExecutor(insertListeners, mappedStatement, new InsertExecutorHandler(statementHandler, transaction.getConnection()));
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        DeleteListener[] deleteListeners = null;
        if (listenerFactory != null) {
            deleteListeners = listenerFactory.getDeleteListener();
        }
        return doExecutor(deleteListeners, mappedStatement, new DeleteExecutorHandler(statementHandler, transaction.getConnection()));
    }


    protected Object doExecutor(Listener[] listeners, MappedStatement mappedStatement, ExecutorHandler executorHandler) throws SQLException {
        if (before(listeners, mappedStatement)) {
            Object result = null;
            try {
                result = executorHandler.execute(mappedStatement);
            } catch (Exception e) {
                exception(listeners, e, mappedStatement);
                throw e;
            } finally {
                statementHandler.close();
            }
            return afterReturn(listeners, result, mappedStatement);
        } else {
            return null;
        }
    }

    protected abstract StatementHandler createStatementHandler();

    protected ResultSetHandler createResultSetHandler() {
        return new DefaultResultSetHandler(configuration, this);
    }

    @Override
    public boolean isAutoCommit() {
        return transaction.isAutoCommit();
    }

    @Override
    public void commit() {
        transaction.commit();
    }

    @Override
    public void rollback() {
        transaction.rollback();
    }

    @Override
    public void close() {
        transaction.close();
    }

    protected boolean before(Listener[] listeners, MappedStatement mappedStatement) {
        boolean success = true;
        Listener[] tempListeners = mappedStatement.getListeners();
        if (!ObjectUtil.isNull(tempListeners)) {
            for (Listener listener : tempListeners) {
                success = success & listener.before(mappedStatement);
            }
        }
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                success = success & listener.before(mappedStatement);
            }
        }
        return success;
    }

    protected Object afterReturn(Listener[] listeners, Object result, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                result = listener.afterReturn(result, mappedStatement);
            }
        }
        return result;
    }

    protected void exception(Listener[] listeners, Exception e, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                listener.exception(e, mappedStatement);
            }
        }
    }
}
