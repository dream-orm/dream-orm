package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertExecutorHandler implements ExecutorHandler {
    protected StatementHandler statementHandler;
    private Connection connection;

    public InsertExecutorHandler(StatementHandler statementHandler, Connection connection) {
        this.statementHandler = statementHandler;
        this.connection = connection;
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        String[] generatedKeys = mappedStatement.getGeneratedKeys();
        if (!ObjectUtil.isNull(generatedKeys)) {
            statementHandler.prepare(connection, mappedStatement, Statement.RETURN_GENERATED_KEYS);
            int result = statementHandler.executeUpdate(mappedStatement);
            ResultSet generatedKeysResult = statementHandler.getStatement().getGeneratedKeys();
            int index=0;
            ObjectWrapper targetWrapper = ObjectWrapper.wrapper(mappedStatement.getArg());
            while (generatedKeysResult.next()){
                long value = generatedKeysResult.getLong(index+1);
                targetWrapper.set(generatedKeys[index++],value);
            }
            return result;
        } else {
            statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
            return statementHandler.executeUpdate(mappedStatement);
        }
    }
}