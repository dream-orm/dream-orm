package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertExecutorHandler extends UpdateExecutorHandler {
    public InsertExecutorHandler(StatementHandler statementHandler, Connection connection) throws SQLException {
        super(statementHandler, connection);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        Object result=super.execute(mappedStatement);
        ResultSet generatedKeys = statementHandler.getStatement().getGeneratedKeys();
        if(generatedKeys.next()){
            result=generatedKeys.getLong(0);
        }
        return result;
    }
}
