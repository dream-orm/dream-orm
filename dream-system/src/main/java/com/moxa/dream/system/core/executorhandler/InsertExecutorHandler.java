package com.moxa.dream.system.core.executorhandler;

import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            int index = 1;
            List<Long> resultList = new ArrayList<>();
            while (generatedKeysResult.next()) {
                Long value = generatedKeysResult.getLong(index++);
                resultList.add(value);
            }
            return resultList;
        } else {
            return super.execute(mappedStatement);
        }
    }
}