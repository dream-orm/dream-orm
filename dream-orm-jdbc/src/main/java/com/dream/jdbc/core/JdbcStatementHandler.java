package com.dream.jdbc.core;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.statementhandler.StatementHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcStatementHandler implements StatementHandler<PreparedStatement> {
    private StatementSetter statementSetter;

    public JdbcStatementHandler(StatementSetter statementSetter) {
        this.statementSetter = statementSetter;
    }

    @Override
    public ResultSet query(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        statementSetter.setter(statement, mappedStatement);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    @Override
    public Object update(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        statementSetter.setter(statement, mappedStatement);
        return statement.executeUpdate();
    }

    @Override
    public Object batch(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
        List<Object> resultList = new ArrayList<>();
        while (batchMappedStatement.hasNext()) {
            BatchMappedStatement nextBatchMappedStatement = batchMappedStatement.next();
            for (MappedStatement ms : nextBatchMappedStatement.getMappedStatementList()) {
                statementSetter.setter(statement, ms);
                statement.addBatch();
            }
            int[] result = statement.executeBatch();
            statement.clearBatch();
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public PreparedStatement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.prepareStatement(mappedStatement.getSql());
    }
}
