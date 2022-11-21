package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.transaction.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcExecutor implements Executor {
    protected Transaction transaction;
    protected StatementHandler statementHandler;
    protected ResultSetHandler resultSetHandler;
    protected SessionFactory sessionFactory;


    public JdbcExecutor(Transaction transaction, StatementHandler statementHandler, ResultSetHandler resultSetHandler, SessionFactory sessionFactory) {
        this.transaction = transaction;
        this.statementHandler = statementHandler;
        this.resultSetHandler = resultSetHandler;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> {
            ResultSet resultSet = statementHandler.executeQuery(mappedStatement);
            return resultSetHandler.result(resultSet, mappedStatement, this);
        });
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> statementHandler.executeUpdate(mappedStatement));
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> statementHandler.executeUpdate(mappedStatement));
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> statementHandler.executeUpdate(mappedStatement));
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements) throws SQLException {
        return execute(mappedStatements.get(0), (ms) -> statementHandler.executeBatch(mappedStatements));
    }


    protected Object execute(MappedStatement mappedStatement, Function<MappedStatement, Object> function) throws SQLException {
        try {
            statementHandler.prepare(transaction.getConnection(), mappedStatement);
            return function.apply(mappedStatement);
        } finally {
            statementHandler.close();
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

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
