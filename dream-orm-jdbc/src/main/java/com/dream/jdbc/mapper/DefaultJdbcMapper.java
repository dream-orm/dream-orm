package com.dream.jdbc.mapper;

import com.dream.jdbc.core.JdbcResultSetHandler;
import com.dream.jdbc.core.JdbcStatementHandler;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.*;
import com.dream.system.core.session.Session;

import java.util.List;

public class DefaultJdbcMapper implements JdbcMapper {
    private Session session;

    public DefaultJdbcMapper(Session session) {
        this.session = session;
    }

    @Override
    public int execute(String sql, StatementSetter statementSetter) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setCache(false);
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedSql mappedSql = new MappedSql(Command.UPDATE, sql, null);
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .mappedSql(mappedSql).build();
        return (int) session.execute(mappedStatement);
    }

    @Override
    public <T> int[] batchExecute(String sql, List<T> argList, StatementSetter<T> statementSetter) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setCache(false);
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedSql mappedSql = new MappedSql(Command.BATCH, sql, null);
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .mappedSql(mappedSql)
                .arg(argList).build();
        return (int[]) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> queryForList(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        JdbcResultSetHandler jdbcResultSetHandler = new JdbcResultSetHandler(rowMapping);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setCache(false);
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setResultSetHandler(jdbcResultSetHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedSql mappedSql = new MappedSql(Command.QUERY, sql, null);
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .mappedSql(mappedSql).build();
        return (List<T>) session.execute(mappedStatement);
    }
}
