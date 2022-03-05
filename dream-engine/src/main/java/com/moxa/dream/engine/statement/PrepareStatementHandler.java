package com.moxa.dream.engine.statement;

import com.moxa.dream.module.mapped.MappedParam;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.*;
import java.util.List;

public class PrepareStatementHandler extends BaseStatementHandler {

    @Override
    protected Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(mappedStatement.getSql());
        return preparedStatement;
    }

    @Override
    protected void parameter(Statement statement, MappedStatement mappedStatement) throws SQLException {
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        if (!ObjectUtil.isNull(mappedParamList)) {
            for (int i = 0; i < mappedParamList.size(); i++) {
                MappedParam mappedParam = mappedParamList.get(i);
                mappedParam.setParam((PreparedStatement) statement, i + 1);
            }
        }
    }

    @Override
    protected ResultSet executeQuery(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return ((PreparedStatement) statement).executeQuery();
    }

    @Override
    protected int executeUpdate(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return ((PreparedStatement) statement).executeUpdate();
    }

    @Override
    protected int addBatch(Statement statement, MappedStatement mappedStatement) throws SQLException {
        ((PreparedStatement) statement).addBatch();
        return 0;
    }

    @Override
    public int[] flushStatement(boolean rollback) throws SQLException {
        return null;
    }

    @Override
    public void close() throws SQLException {
        if (statement != null && !statement.isClosed()) {
            statement.close();
            statement = null;
        }
    }

}
