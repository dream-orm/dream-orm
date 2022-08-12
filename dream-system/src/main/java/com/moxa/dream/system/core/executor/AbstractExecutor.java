package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executorhandler.DefaultExecutorHandler;
import com.moxa.dream.system.core.executorhandler.ExecutorHandler;
import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;

public abstract class AbstractExecutor implements Executor {
    protected Transaction transaction;
    protected ExecutorHandler executorHandler;
    protected StatementHandler statementHandler;
    protected ListenerFactory listenerFactory;

    public AbstractExecutor(Configuration configuration, boolean autoCommit) {
        this.transaction = configuration.getTransaction(autoCommit);
        this.listenerFactory = configuration.getListenerFactory();
        this.statementHandler = getStatementHandler();
        this.executorHandler = getExecutorHandler(configuration);
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            QueryListener[] queryListeners = listenerFactory.getQueryListener();
            if (!ObjectUtil.isNull(queryListeners)) {
                if (before(queryListeners, mappedStatement)) {
                    try {
                        Object result = executorHandler.query(statementHandler, transaction.getConnection(), mappedStatement, this);
                        return afterReturn(queryListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exception(queryListeners, e, mappedStatement);
                        throw e;
                    } finally {
                        statementHandler.close();
                    }
                } else {
                    return null;
                }
            }
        }
        try {
            return executorHandler.query(statementHandler, transaction.getConnection(), mappedStatement, this);
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            UpdateListener[] updateListeners = listenerFactory.getUpdateListener();
            if (!ObjectUtil.isNull(updateListeners)) {
                if (before(updateListeners, mappedStatement)) {
                    try {
                        Object result = executorHandler.update(statementHandler, transaction.getConnection(), mappedStatement, this);
                        return afterReturn(updateListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exception(updateListeners, e, mappedStatement);
                        throw e;
                    } finally {
                        statementHandler.close();
                    }
                } else {
                    return null;
                }
            }
        }
        try {
            return executorHandler.update(statementHandler, transaction.getConnection(), mappedStatement, this);
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            InsertListener[] insertListeners = listenerFactory.getInsertListener();
            if (!ObjectUtil.isNull(insertListeners)) {
                if (before(insertListeners, mappedStatement)) {
                    try {
                        Object result = executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
                        return afterReturn(insertListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exception(insertListeners, e, mappedStatement);
                        throw e;
                    } finally {
                        statementHandler.close();
                    }
                } else {
                    return null;
                }
            }
        }
        try {
            return executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            DeleteListener[] deleteListeners = listenerFactory.getDeleteListener();
            if (!ObjectUtil.isNull(deleteListeners)) {
                if (before(deleteListeners, mappedStatement)) {
                    try {
                        Object result = executorHandler.delete(statementHandler, transaction.getConnection(), mappedStatement, this);
                        return afterReturn(deleteListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exception(deleteListeners, e, mappedStatement);
                        throw e;
                    } finally {
                        statementHandler.close();
                    }
                } else {
                    return null;
                }
            }
        }
        try {
            return executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
        } finally {
            statementHandler.close();
        }
    }

    protected abstract StatementHandler getStatementHandler();

    protected ExecutorHandler getExecutorHandler(Configuration configuration) {
        ExecutorHandler executorHandler = new DefaultExecutorHandler();
        PluginFactory pluginFactory = configuration.getPluginFactory();
        if (pluginFactory != null) {
            executorHandler = (ExecutorHandler) pluginFactory.plugin(executorHandler);
        }
        return executorHandler;
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
        for (Listener listener : listeners) {
            success = success & listener.before(mappedStatement,this);
        }
        return success;
    }

    protected Object afterReturn(Listener[] listeners, Object result, MappedStatement mappedStatement) {
        for (Listener listener : listeners) {
            result = listener.afterReturn(result, mappedStatement,this);
        }
        return result;
    }

    protected void exception(Listener[] listeners, Exception e, MappedStatement mappedStatement) {
        for (Listener listener : listeners) {
            listener.exception(e, mappedStatement,this);
        }
    }
}
