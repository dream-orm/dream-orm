package com.moxa.dream.module.core.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.module.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.plugin.factory.PluginFactory;
import com.moxa.dream.module.transaction.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractExecutor implements Executor {
    protected Transaction transaction;
    protected Configuration configuration;
    protected StatementHandler statementHandler;
    protected ResultSetHandler resultSetHandler;
    protected PluginFactory pluginFactory;

    public AbstractExecutor(Configuration configuration, boolean autoCommit) {
        this.configuration = configuration;
        this.transaction = configuration.getTransaction(autoCommit);
        this.pluginFactory = configuration.getPluginFactory();
        this.statementHandler = getStatementHandler();
        this.resultSetHandler = getResultSetHandler();
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        try {
            return doQuery(mappedStatement);
        } catch (SQLException e) {
            throw e;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        try {
            return doUpdate(mappedStatement);
        } catch (SQLException e) {
            throw e;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        try {
            Object result = doUpdate(mappedStatement);
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            statementHandler.close();
        }
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        try {
            return doUpdate(mappedStatement);
        } catch (SQLException e) {
            throw e;
        } finally {
            statementHandler.close();
        }
    }

    protected Object doQuery(MappedStatement mappedStatement) throws SQLException {
        ResultSet resultSet = statementHandler.doQuery(transaction.getConnection(), mappedStatement);
        return resultSetHandler.result(resultSet, mappedStatement);
    }

    protected Object doUpdate(MappedStatement mappedStatement) throws SQLException {
        return statementHandler.doUpdate(transaction.getConnection(), mappedStatement);
    }

    @Override
    public StatementHandler getStatementHandler() {
        if (statementHandler == null) {
            statementHandler = createStatementHandler();
            if (pluginFactory != null) {
                statementHandler = (StatementHandler) pluginFactory.plugin(statementHandler);
            }
        }
        return statementHandler;
    }

    @Override
    public ResultSetHandler getResultSetHandler() {
        if (resultSetHandler == null) {
            resultSetHandler = createResultSetHandler();
            if (pluginFactory != null) {
                resultSetHandler = (ResultSetHandler) pluginFactory.plugin(resultSetHandler);
            }
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
        if (transaction != null)
            transaction.close();
    }

}
