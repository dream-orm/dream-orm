package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.executorhandler.ExecutorHandler;
import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractExecutor implements Executor {
    protected Transaction transaction;
    protected StatementHandler statementHandler;
    protected ListenerFactory listenerFactory;
    protected ResultSetHandler resultSetHandler;

    public AbstractExecutor(Configuration configuration, Transaction transaction) {
        this.transaction = transaction;
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
        return execute(mappedStatement, queryListeners, connection -> {
            statementHandler.prepare(connection, mappedStatement);
            ResultSet resultSet = statementHandler.executeQuery(mappedStatement);
            return resultSetHandler.result(resultSet, mappedStatement, this);
        });
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        UpdateListener[] updateListeners = null;
        if (listenerFactory != null) {
            updateListeners = listenerFactory.getUpdateListener();
        }
        return execute(mappedStatement, updateListeners, connection -> {
            statementHandler.prepare(connection, mappedStatement);
            return statementHandler.executeUpdate(mappedStatement);
        });
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        InsertListener[] insertListeners = null;
        if (listenerFactory != null) {
            insertListeners = listenerFactory.getInsertListener();
        }
        return execute(mappedStatement, insertListeners, connection -> {
            statementHandler.prepare(connection, mappedStatement);
            return statementHandler.executeUpdate(mappedStatement);
        });
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        DeleteListener[] deleteListeners = null;
        if (listenerFactory != null) {
            deleteListeners = listenerFactory.getDeleteListener();
        }
        return execute(mappedStatement, deleteListeners, connection -> {
            statementHandler.prepare(connection, mappedStatement);
            return statementHandler.executeUpdate(mappedStatement);
        });
    }

    @Override
    public int[] batch(MappedStatement[] mappedStatements) throws SQLException {
        statementHandler.prepare(transaction.getConnection(), mappedStatements[0]);
        int[] values = statementHandler.executeBatch(mappedStatements);
        return values;
    }

    protected Object execute(MappedStatement mappedStatement, Listener[] listeners, ExecutorHandler executorHandler) throws SQLException {
        if (!ObjectUtil.isNull(listeners)) {
            if (beforeListeners(listeners, mappedStatement)) {
                try {
                    initActions(mappedStatement);
                    Object result = executorHandler.execute(transaction.getConnection());
                    destroyActions(mappedStatement);
                    return afterReturnListeners(listeners, result, mappedStatement);
                } catch (Exception e) {
                    exceptionListeners(listeners, e, mappedStatement);
                    throw e;
                } finally {
                    statementHandler.close();
                }
            } else {
                return null;
            }
        } else {
            try {
                initActions(mappedStatement);
                Object result = executorHandler.execute(transaction.getConnection());
                destroyActions(mappedStatement);
                return result;
            } finally {
                statementHandler.close();
            }
        }
    }

    protected abstract StatementHandler getStatementHandler();

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

    protected ResultSetHandler getResultSetHandler() {
        return new DefaultResultSetHandler();
    }
}
