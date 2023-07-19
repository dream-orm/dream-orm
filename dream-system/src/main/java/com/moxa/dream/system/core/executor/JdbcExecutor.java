package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
    public Object execute(MappedStatement mappedStatement, Session session) throws SQLException {
        Object result;
        Statement statement = null;
        try {
            statement = statementHandler(mappedStatement).prepare(transaction.getConnection(), mappedStatement);
            Command command = mappedStatement.getCommand();
            switch (command) {
                case QUERY:
                    result = query(statement, mappedStatement, session);
                    break;
                case UPDATE:
                case DELETE:
                case TRUNCATE:
                case DROP:
                    result = update(statement, mappedStatement, session);
                    break;
                case INSERT:
                    result = insert(statement, mappedStatement, session);
                    break;
                case BATCH:
                    BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
                    result = batch(statement, batchMappedStatement, session);
                    break;
                default:
                    result = executeNone(mappedStatement);
                    break;
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }

    protected Object query(Statement statement, MappedStatement mappedStatement, Session session) throws SQLException {
        ResultSet resultSet = statementHandler(mappedStatement).query(statement, mappedStatement);
        try {
            return resultSetHandler(mappedStatement).result(resultSet, mappedStatement, session);
        } finally {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        }
    }

    protected Object update(Statement statement, MappedStatement mappedStatement, Session session) throws SQLException {
        Object result = statementHandler(mappedStatement).update(statement, mappedStatement);
        return result;
    }

    protected Object insert(Statement statement, MappedStatement mappedStatement, Session session) throws SQLException {
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
    }

    protected Object batch(Statement statement, BatchMappedStatement batchMappedStatement, Session session) throws SQLException {
        List<Object> resultList = new ArrayList<>();
        while (batchMappedStatement.hasNext()) {
            resultList.add(statementHandler(batchMappedStatement.next()).batch(statement, batchMappedStatement));
        }
        return resultList;
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

    protected Object executeNone(MappedStatement mappedStatement) {
        throw new DreamRunTimeException("SQL类型" + mappedStatement.getCommand() + "不支持");
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
