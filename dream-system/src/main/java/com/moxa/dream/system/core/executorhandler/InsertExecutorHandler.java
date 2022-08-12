//package com.moxa.dream.system.core.executorhandler;
//
//import com.moxa.dream.system.core.statementhandler.StatementHandler;
//import com.moxa.dream.system.mapped.MappedStatement;
//import com.moxa.dream.util.common.ObjectUtil;
//import com.moxa.dream.util.common.ObjectWrapper;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class InsertExecutorHandler extends AbstractExecutorHandler {
//    public InsertExecutorHandler(StatementHandler statementHandler, Connection connection) {
//        super(statementHandler, connection);
//    }
//
//    @Override
//    public Object execute(MappedStatement mappedStatement) throws SQLException {
//        String keyProperty = mappedStatement.getKeyProperty();
//        if (!ObjectUtil.isNull(keyProperty)) {
//            statementHandler.prepare(connection, mappedStatement, Statement.RETURN_GENERATED_KEYS);
//            int result = statementHandler.executeUpdate(mappedStatement);
//            ResultSet generatedKeysResult = statementHandler.getStatement().getGeneratedKeys();
//            if (generatedKeysResult.next()) {
//                Long value = generatedKeysResult.getLong(1);
//                ObjectWrapper.wrapper(mappedStatement.getArg()).set(keyProperty, value);
//            }
//            return result;
//        } else {
//            return super.execute(mappedStatement);
//        }
//    }
//}
