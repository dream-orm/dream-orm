package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertExecutorHandler extends AbstractExecutorHandler {
    public InsertExecutorHandler(StatementHandler statementHandler, Connection connection) throws SQLException {
        super(statementHandler, connection);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        Object result = super.execute(mappedStatement);
        String[] generatedKeys = mappedStatement.getGeneratedKeys();
        if (!ObjectUtil.isNull(generatedKeys)) {
            Object arg = mappedStatement.getArg();
            ObjectWrapper paramWrapper = ObjectWrapper.wrapper(arg);
            ResultSet generatedKeysResult = statementHandler.getStatement().getGeneratedKeys();
            int index = 0;
            while (generatedKeysResult.next()) {
                result = generatedKeysResult.getLong(0);
                paramWrapper.set(generatedKeys[index++], result);
            }
        }
        return result;
    }
}
