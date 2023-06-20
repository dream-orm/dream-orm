package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.typehandler.handler.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class JdbcExecutor implements Executor {
    protected Transaction transaction;
    protected StatementHandler statementHandler;
    protected ResultSetHandler resultSetHandler;


    public JdbcExecutor(Transaction transaction, StatementHandler statementHandler, ResultSetHandler resultSetHandler) {
        this.transaction = transaction;
        this.statementHandler = statementHandler;
        this.resultSetHandler = resultSetHandler;
    }

    @Override
    public Object query(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, statement -> {
            ResultSet resultSet = statementHandler(mappedStatement).query(statement, mappedStatement);
            try {
                return resultSetHandler(mappedStatement).result(resultSet, mappedStatement, session);
            } finally {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            }
        });
    }

    @Override
    public Object update(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, statement -> statementHandler(mappedStatement).update(statement, mappedStatement));
    }

    @Override
    public Object insert(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, statement -> {
            Object result = statementHandler(mappedStatement).update(statement, mappedStatement);
            String[] columnNames = mappedStatement.getColumnNames();
            if (columnNames != null && columnNames.length > 0) {
                Object[] results = new Object[columnNames.length];
                TypeHandler[] columnTypeHandlers = mappedStatement.getColumnTypeHandlers();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    for (int i = 0; i < columnNames.length; i++) {
                        results[i] = columnTypeHandlers[i].getResult(generatedKeys, columnNames[i], Types.NULL);
                    }
                    result = results;
                }
            }
            return result;
        });
    }

    @Override
    public Object delete(MappedStatement mappedStatement, Session session) throws SQLException {
        return update(mappedStatement, session);
    }

    @Override
    public Object batch(BatchMappedStatement batchMappedStatement, Session session) throws SQLException {
        return execute(batchMappedStatement, statement -> statementHandler(batchMappedStatement).batch(statement, batchMappedStatement));
    }

    @Override
    public Object truncate(MappedStatement mappedStatement, Session session) throws SQLException {
        return update(mappedStatement, session);
    }

    @Override
    public Object drop(MappedStatement mappedStatement, Session session) throws SQLException {
        return update(mappedStatement, session);
    }

    protected Object execute(MappedStatement mappedStatement, Function<Statement, Object> function) throws SQLException {
        Statement statement = null;
        try {
            statement = statementHandler(mappedStatement).prepare(transaction.getConnection(), mappedStatement);
            return function.apply(statement);
        } finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        }
    }

    protected StatementHandler statementHandler(MappedStatement mappedStatement) {
        StatementHandler statementHandler = mappedStatement.getStatementHandler();
        if (statementHandler == null) {
            return this.statementHandler;
        } else {
            return statementHandler;
        }
    }

    protected ResultSetHandler resultSetHandler(MappedStatement mappedStatement) {
        ResultSetHandler resultSetHandler = mappedStatement.getResultSetHandler();
        if (resultSetHandler == null) {
            return this.resultSetHandler;
        } else {
            return resultSetHandler;
        }
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
}
