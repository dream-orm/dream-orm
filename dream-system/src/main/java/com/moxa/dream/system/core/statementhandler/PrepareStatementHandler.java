package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.mapped.MappedParam;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.*;
import java.util.List;

public class PrepareStatementHandler implements StatementHandler {
    private PreparedStatement statement;

    @Override
    public void prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        statement = connection.prepareStatement(mappedStatement.getSql(), mappedStatement.getColumnNames());
    }

    protected void doParameter(MappedStatement mappedStatement) throws SQLException {
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        if (!ObjectUtil.isNull(mappedParamList)) {
            for (int i = 0; i < mappedParamList.size(); i++) {
                MappedParam mappedParam = mappedParamList.get(i);
                mappedParam.setParam(statement, i + 1);
            }
        }
    }

    protected void doTimeOut(MappedStatement mappedStatement) throws SQLException {
        int timeOut = mappedStatement.getTimeOut();
        if (timeOut != 0) {
            statement.setQueryTimeout(timeOut);
        }
    }

    @Override
    public ResultSet executeQuery(MappedStatement mappedStatement) throws SQLException {
        doParameter(mappedStatement);
        doTimeOut(mappedStatement);
        return statement.executeQuery();
    }

    @Override
    public int executeUpdate(MappedStatement mappedStatement) throws SQLException {
        doParameter(mappedStatement);
        return statement.executeUpdate();
    }

    @Override
    public void addBatch(MappedStatement mappedStatement) throws SQLException {
        doParameter(mappedStatement);
        statement.addBatch();
    }

    @Override
    public void flushStatement(boolean rollback) {
        if (!rollback) {
            try {
                statement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
