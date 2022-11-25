package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedParam;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.*;
import java.util.List;

public class PrepareStatementHandler implements StatementHandler<PreparedStatement> {

    @Override
    public PreparedStatement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.prepareStatement(mappedStatement.getSql(), mappedStatement.getColumnNames());
    }

    protected void doParameter(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        if (!ObjectUtil.isNull(mappedParamList)) {
            for (int i = 0; i < mappedParamList.size(); i++) {
                MappedParam mappedParam = mappedParamList.get(i);
                mappedParam.setParam(statement, i + 1);
            }
        }
    }

    protected void doTimeOut(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        int timeOut = mappedStatement.getTimeOut();
        if (timeOut != 0) {
            statement.setQueryTimeout(timeOut);
        }
    }

    @Override
    public ResultSet query(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        doParameter(statement, mappedStatement);
        doTimeOut(statement, mappedStatement);
        return statement.executeQuery();
    }

    @Override
    public Object update(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        doParameter(statement, mappedStatement);
        return statement.executeUpdate();
    }

    @Override
    public Object insert(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        doParameter(statement, mappedStatement);
        Object result = statement.executeUpdate();
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

    @Override
    public Object delete(PreparedStatement statement, MappedStatement mappedStatement) throws SQLException {
        doParameter(statement, mappedStatement);
        return statement.executeUpdate();
    }

    @Override
    public Object batch(PreparedStatement statement, BatchMappedStatement batchMappedStatement) throws SQLException {
        for (MappedStatement mappedStatement : batchMappedStatement.getMappedStatementList()) {
            doParameter(statement, mappedStatement);
            statement.addBatch();
        }
        int[] result = statement.executeBatch();
        statement.clearBatch();
        return result;
    }
}
