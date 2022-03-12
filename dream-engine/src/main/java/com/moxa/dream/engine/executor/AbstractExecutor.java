package com.moxa.dream.engine.executor;

import com.moxa.dream.engine.result.DefaultResultSetHandler;
import com.moxa.dream.engine.result.ResultSetHandler;
import com.moxa.dream.engine.statement.StatementHandler;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.plugin.PluginFactory;
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
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        StatementHandler statementHandler = null;
        ResultSetHandler resultSetHandler;
        try {
            statementHandler = getStatementHandler(mappedStatement);
            resultSetHandler = getResultSetHandler(mappedStatement);
            ResultSet resultSet = statementHandler.doQuery(transaction.getConnection(mappedStatement), mappedStatement);
            return resultSetHandler.result(resultSet, mappedStatement);
        } finally {
            if (statementHandler != null) {
                statementHandler.close();
            }
        }
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        try {
            statementHandler = getStatementHandler(mappedStatement);
            return statementHandler.doUpdate(transaction.getConnection(mappedStatement), mappedStatement);
        } finally {
            if (statementHandler != null) {
                statementHandler.close();
            }
        }
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        return update(mappedStatement);
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        return update(mappedStatement);
    }

    @Override
    public StatementHandler getStatementHandler(MappedStatement mappedStatement) {
        if (statementHandler == null) {
            statementHandler = createStatementHandler(mappedStatement);
            if (pluginFactory != null) {
                statementHandler = (StatementHandler) pluginFactory.plugin(statementHandler);
            }
        }
        return statementHandler;
    }

    @Override
    public ResultSetHandler getResultSetHandler(MappedStatement mappedStatement) {
        if (resultSetHandler == null) {
            resultSetHandler = createResultSetHandler(mappedStatement);
            if (pluginFactory != null) {
                resultSetHandler = (ResultSetHandler) pluginFactory.plugin(resultSetHandler);
            }
        }
        return resultSetHandler;
    }

    protected abstract StatementHandler createStatementHandler(MappedStatement mappedStatement);

    protected ResultSetHandler createResultSetHandler(MappedStatement mappedStatement) {
        return new DefaultResultSetHandler(configuration, this);
    }

    @Override
    public boolean isAutoCommit() {
        return transaction.isAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        if (transaction != null)
            transaction.commit();
    }

    @Override
    public void rollback() throws SQLException {
        if (transaction != null)
            transaction.rollback();
    }

    @Override
    public void close() throws SQLException {
        if (transaction != null)
            transaction.close();
    }

}
