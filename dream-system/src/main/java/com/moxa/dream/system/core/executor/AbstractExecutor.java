package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executorhandler.DefaultExecutorHandler;
import com.moxa.dream.system.core.executorhandler.ExecutorHandler;
import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.action.Action;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractExecutor implements Executor {
    protected Transaction transaction;
    protected ExecutorHandler executorHandler;
    protected StatementHandler statementHandler;
    protected ListenerFactory listenerFactory;

    public AbstractExecutor(Configuration configuration, Transaction transaction) {
        this.transaction = transaction;
        this.listenerFactory = configuration.getListenerFactory();
        this.statementHandler = getStatementHandler();
        this.executorHandler = getExecutorHandler(configuration);
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            QueryListener[] queryListeners = listenerFactory.getQueryListener();
            if (!ObjectUtil.isNull(queryListeners)) {
                if (beforeListeners(queryListeners, mappedStatement)) {
                    try {
                        initActions(mappedStatement);
                        Object result = executorHandler.query(statementHandler, transaction.getConnection(), mappedStatement, this);
                        destroyActions(mappedStatement);
                        return afterReturnListeners(queryListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exceptionListeners(queryListeners, e, mappedStatement);
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
            initActions(mappedStatement);
            Object result = executorHandler.query(statementHandler, transaction.getConnection(), mappedStatement, this);
            destroyActions(mappedStatement);
            return result;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            UpdateListener[] updateListeners = listenerFactory.getUpdateListener();
            if (!ObjectUtil.isNull(updateListeners)) {
                if (beforeListeners(updateListeners, mappedStatement)) {
                    try {
                        initActions(mappedStatement);
                        Object result = executorHandler.update(statementHandler, transaction.getConnection(), mappedStatement, this);
                        destroyActions(mappedStatement);
                        return afterReturnListeners(updateListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exceptionListeners(updateListeners, e, mappedStatement);
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
            initActions(mappedStatement);
            Object result = executorHandler.update(statementHandler, transaction.getConnection(), mappedStatement, this);
            destroyActions(mappedStatement);
            return result;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            InsertListener[] insertListeners = listenerFactory.getInsertListener();
            if (!ObjectUtil.isNull(insertListeners)) {
                if (beforeListeners(insertListeners, mappedStatement)) {
                    try {
                        initActions(mappedStatement);
                        Object result = executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
                        destroyActions(mappedStatement);
                        return afterReturnListeners(insertListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exceptionListeners(insertListeners, e, mappedStatement);
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
            initActions(mappedStatement);
            Object result = executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
            destroyActions(mappedStatement);
            return result;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        if (listenerFactory != null) {
            DeleteListener[] deleteListeners = listenerFactory.getDeleteListener();
            if (!ObjectUtil.isNull(deleteListeners)) {
                if (beforeListeners(deleteListeners, mappedStatement)) {
                    try {
                        initActions(mappedStatement);
                        Object result = executorHandler.delete(statementHandler, transaction.getConnection(), mappedStatement, this);
                        destroyActions(mappedStatement);
                        return afterReturnListeners(deleteListeners, result, mappedStatement);
                    } catch (Exception e) {
                        exceptionListeners(deleteListeners, e, mappedStatement);
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
            initActions(mappedStatement);
            Object result = executorHandler.insert(statementHandler, transaction.getConnection(), mappedStatement, this);
            destroyActions(mappedStatement);
            return result;
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

    @Override
    public Statement getStatement() {
        return statementHandler.getStatement();
    }

    protected boolean beforeListeners(Listener[] listeners, MappedStatement mappedStatement) {
        boolean success = true;
        for (Listener listener : listeners) {
            success = success & listener.before(mappedStatement);
        }
        return success;
    }

    protected Object afterReturnListeners(Listener[] listeners, Object result, MappedStatement mappedStatement) {
        for (Listener listener : listeners) {
            result = listener.afterReturn(result, mappedStatement);
        }
        return result;
    }

    protected void exceptionListeners(Listener[] listeners, Exception e, MappedStatement mappedStatement) {
        for (Listener listener : listeners) {
            listener.exception(e, mappedStatement);
        }
    }

    protected void initActions(MappedStatement mappedStatement) {
        Action[] initActionList = mappedStatement.getInitActionList();
        if (!ObjectUtil.isNull(initActionList)) {
            Object arg = mappedStatement.getArg();
            try {
                for (Action action : initActionList) {
                    action.doAction(this, arg);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void destroyActions(MappedStatement mappedStatement) {
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        if (!ObjectUtil.isNull(destroyActionList)) {
            Object arg = mappedStatement.getArg();
            try {
                for (Action action : destroyActionList) {
                    action.doAction(this, arg);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
