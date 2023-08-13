package com.dream.jdbc.core;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.statementhandler.StatementHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcStatementHandler implements StatementHandler<PreparedStatement> {
    private StatementSetter statementSetter;

    public JdbcStatementHandler(StatementSetter statementSetter) {
        this.statementSetter = statementSetter;
    }

    @Override
    public ResultSet query(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        statementSetter.setter(statement, mappedStatement.getConfiguration(), mappedStatement.getArg());
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    @Override
    public Object update(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        statementSetter.setter(statement, mappedStatement.getConfiguration(), mappedStatement.getArg());
        return statement.executeUpdate();
    }

    @Override
    public Object batch(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        List<Object> argList = (List<Object>) mappedStatement.getArg();
        for (Object arg : argList) {
            statementSetter.setter(statement, mappedStatement.getConfiguration(), arg);
            statement.addBatch();
        }
        return statement.executeBatch();
    }

    @Override
    public PreparedStatement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.prepareStatement(mappedStatement.getSql());
    }
}
