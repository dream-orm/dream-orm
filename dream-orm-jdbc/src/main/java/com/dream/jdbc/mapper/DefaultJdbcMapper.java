package com.dream.jdbc.mapper;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.read.ExprReader;
import com.dream.jdbc.core.JdbcBatchMappedStatement;
import com.dream.jdbc.core.JdbcResultSetHandler;
import com.dream.jdbc.core.JdbcStatementHandler;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.*;
import com.dream.system.core.session.Session;
import com.dream.util.common.LowHashSet;

import java.util.List;
import java.util.Set;

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
        MappedSql mappedSql = new MappedSql(Command.UPDATE, sql, tableSet(sql));
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .mappedSql(mappedSql).build();
        return (int) session.execute(mappedStatement);
    }

    @Override
    public <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter, int batchSize) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setCache(false);
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedSql mappedSql = new MappedSql(Command.BATCH, sql, tableSet(sql));
        JdbcBatchMappedStatement jdbcBatchMappedStatement = new JdbcBatchMappedStatement(methodInfo, argList, mappedSql);
        return (List<Object>) session.execute(jdbcBatchMappedStatement);
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

    protected Set<String> tableSet(String sql) {
        ExprReader exprReader = new ExprReader(sql);
        ExprInfo exprInfo = exprReader.push();
        switch (exprInfo.getExprType()) {
            case UPDATE:
            case DELETE:
                exprInfo = exprReader.push();
                break;
            case DROP:
            case INSERT:
                exprReader.push();
                exprInfo = exprReader.push();
                break;
            default:
                exprInfo = null;
        }
        if (exprInfo != null) {
            LowHashSet tableSet = new LowHashSet();
            tableSet.add(exprInfo.getInfo());
            return tableSet;
        }
        return null;
    }
}
