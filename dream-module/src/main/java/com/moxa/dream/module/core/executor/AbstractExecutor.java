package com.moxa.dream.module.core.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.core.executorhandler.*;
import com.moxa.dream.module.core.listener.*;
import com.moxa.dream.module.core.listener.factory.ListenerFactory;
import com.moxa.dream.module.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.module.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.transaction.Transaction;
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
        this.statementHandler = getStatementHandler();
        this.resultSetHandler = getResultSetHandler();
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
        try {
            before(listeners, mappedStatement);
            Object result = executorHandler.execute(mappedStatement);
            afterReturn(listeners, result, mappedStatement);
            return result;
        } catch (Exception e) {
            exception(listeners, e, mappedStatement);
            throw e;
        } finally {
            statementHandler.close();
            after(listeners, mappedStatement);
        }
    }

    @Override
    public StatementHandler getStatementHandler() {
        if (statementHandler == null) {
            statementHandler = createStatementHandler();
        }
        return statementHandler;
    }

    @Override
    public ResultSetHandler getResultSetHandler() {
        if (resultSetHandler == null) {
            resultSetHandler = createResultSetHandler();
        }
        return resultSetHandler;
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

    protected void before(Listener[] listeners, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                listener.before(mappedStatement);
            }
        }
    }

    protected void afterReturn(Listener[] listeners, Object result, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                listener.afterReturn(result, mappedStatement);
            }
        }
    }

    protected void exception(Listener[] listeners, Exception e, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                listener.exception(e, mappedStatement);
            }
        }
    }

    protected void after(Listener[] listeners, MappedStatement mappedStatement) {
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                listener.after(mappedStatement);
            }
        }
    }
}
