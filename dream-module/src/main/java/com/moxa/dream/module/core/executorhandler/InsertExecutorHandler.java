package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertExecutorHandler extends AbstractExecutorHandler {
    public InsertExecutorHandler(StatementHandler statementHandler, Connection connection) {
        super(statementHandler, connection);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        boolean generatedKeys = mappedStatement.isGeneratedKeys();
        if (generatedKeys) {
            statementHandler.prepare(connection, mappedStatement, Statement.RETURN_GENERATED_KEYS);
            statementHandler.executeUpdate(mappedStatement);
            ResultSet generatedKeysResult = statementHandler.getStatement().getGeneratedKeys();
            int index=0;
            int columnCount = generatedKeysResult.getMetaData().getColumnCount();
            Object[]results=new Object[columnCount];
            if(columnCount>0) {
                while (generatedKeysResult.next()) {
                    Object value = generatedKeysResult.getObject(index + 1);
                    results[index++] = value;
                }
            }
            return results;
        } else {
            return super.execute(mappedStatement);
        }
    }
}