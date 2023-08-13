package com.dream.system.core.statementhandler;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleStatementHandler implements StatementHandler<Statement> {
    @Override
    public Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.createStatement();
    }

    protected void doTimeOut(Statement statement, MappedStatement mappedStatement) throws SQLException {
        int timeOut = mappedStatement.getTimeOut();
        if (timeOut != 0) {
            statement.setQueryTimeout(timeOut);
        }
    }

    @Override
    public ResultSet query(Statement statement, MappedStatement mappedStatement) throws SQLException {
        doTimeOut(statement, mappedStatement);
        return statement.executeQuery(mappedStatement.getSql());
    }

    @Override
    public Object update(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object batch(Statement statement, MappedStatement mappedStatement) throws SQLException {
        BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
        List<Object> resultList = new ArrayList<>();
        while (batchMappedStatement.hasNext()) {
            BatchMappedStatement nextBatchMappedStatement = batchMappedStatement.next();
            for (MappedStatement ms : nextBatchMappedStatement.getMappedStatementList()) {
                statement.addBatch(ms.getSql());
            }
            int[] result = statement.executeBatch();
            statement.clearBatch();
            resultList.add(result);
        }
        return resultList;
    }
}
