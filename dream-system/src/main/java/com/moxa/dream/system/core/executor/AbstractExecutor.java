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
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

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

    protected Object execute(MappedStatement mappedStatement, Listener[] listeners, ExecutorHandler executorHandler) throws SQLException {
        if (!ObjectUtil.isNull(listeners)) {
            if (beforeListeners(listeners, mappedStatement)) {
                Object result;
                try {
                    result = executeAction(mappedStatement, executorHandler);
                } catch (Exception e) {
                    exceptionListeners(listeners, e, mappedStatement);
                    throw e;
                }
                return afterReturnListeners(listeners, result, mappedStatement);
            } else {
                return null;
            }
        } else {
            return executeAction(mappedStatement, executorHandler);
        }
    }

    protected Object executeAction(MappedStatement mappedStatement, ExecutorHandler executorHandler) throws SQLException {
        Action[] initActionList = mappedStatement.getInitActionList();
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        try {
            if (!ObjectUtil.isNull(initActionList)) {
                doActions(initActionList, mappedStatement.getArg());
            }
            Object result = executorHandler.execute(transaction.getConnection());
            if (!ObjectUtil.isNull(destroyActionList)) {
                doActions(destroyActionList, mappedStatement.getArg());
            }
            return result;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements) throws SQLException {
        BatchListener[] batchListener = listenerFactory.getBatchListener();
        if (!ObjectUtil.isNull(batchListener)) {
            if (beforeListeners(batchListener, mappedStatements)) {
                Object result;
                try {
                    result = executeAction(mappedStatements, connection -> {
                        statementHandler.prepare(connection, mappedStatements.get(0));
                        return statementHandler.executeBatch(mappedStatements);
                    });
                } catch (Exception e) {
                    exceptionListeners(batchListener, e, mappedStatements);
                    throw e;
                }
                return afterReturnListeners(batchListener, result, mappedStatements);
            } else {
                return null;
            }

        } else {
            return executeAction(mappedStatements, connection -> {
                statementHandler.prepare(connection, mappedStatements.get(0));
                return statementHandler.executeBatch(mappedStatements);
            });
        }
    }

    protected Object executeAction(List<MappedStatement> mappedStatements, ExecutorHandler executorHandler) throws SQLException {
        MethodInfo methodInfo = mappedStatements.get(0).getMethodInfo();
        Action[] initActionList = methodInfo.getInitActionList();
        Action[] destroyActionList = methodInfo.getDestroyActionList();
        Object arg = null;
        try {
            if (!ObjectUtil.isNull(initActionList)) {
                doActions(initActionList, arg = mappedStatements.stream().map(mappedStatement -> mappedStatement.getArg()).collect(Collectors.toList()));
            }
            Object result = executorHandler.execute(transaction.getConnection());
            if (!ObjectUtil.isNull(destroyActionList)) {
                doActions(destroyActionList, arg == null ? mappedStatements.stream().map(mappedStatement -> mappedStatement.getArg()).collect(Collectors.toList()) : arg);
            }
            return result;
        } finally {
            statementHandler.close();
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

    protected boolean beforeListeners(BatchListener[] listeners, List<MappedStatement> mappedStatements) {
        boolean success = true;
        for (BatchListener listener : listeners) {
            success = success & listener.before(mappedStatements);
        }
        return success;
    }

    protected Object afterReturnListeners(BatchListener[] listeners, Object result, List<MappedStatement> mappedStatements) {
        for (BatchListener listener : listeners) {
            result = listener.afterReturn(result, mappedStatements);
        }
        return result;
    }

    protected void exceptionListeners(BatchListener[] listeners, Exception e, List<MappedStatement> mappedStatements) {
        for (BatchListener listener : listeners) {
            listener.exception(e, mappedStatements);
        }
    }

    protected void doActions(Action[] actions, Object arg) {
        try {
            for (Action action : actions) {
                action.doAction(this, arg);
            }
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }

    protected ResultSetHandler getResultSetHandler() {
        return new DefaultResultSetHandler();
    }
}
