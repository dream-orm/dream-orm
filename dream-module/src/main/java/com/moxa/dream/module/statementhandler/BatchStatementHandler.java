package com.moxa.dream.module.statementhandler;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BatchStatementHandler extends BaseStatementHandler {
    private BaseStatementHandler statementHandler;
    private List<BatchResult> batchResultList = new ArrayList<>();
    private BatchResult currentBatchResult;

    public BatchStatementHandler(StatementHandler statementHandler) {
        this.statementHandler = (BaseStatementHandler) statementHandler;
    }


    @Override
    protected Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        Command command = mappedStatement.getCommand();
        Statement statement;
        switch (command) {
            case QUERY:
                flushStatement(false);
                statement = statementHandler.prepare(connection, mappedStatement);
                break;
            default:
                if (currentBatchResult != null && mappedStatement.getSqlKey().equals(currentBatchResult.sqlKey)) {
                    statement = currentBatchResult.statement;
                } else {
                    statement = statementHandler.prepare(connection, mappedStatement);
                    currentBatchResult = new BatchResult(mappedStatement.getSqlKey(), statement, 0);
                    batchResultList.add(currentBatchResult);
                }
                break;
        }
        return statement;
    }

    @Override
    protected void parameter(Statement statement, MappedStatement mappedStatement) throws SQLException {
        statementHandler.parameter(statement, mappedStatement);
    }

    @Override
    protected ResultSet executeQuery(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statementHandler.executeQuery(statement, mappedStatement);
    }

    @Override
    protected int executeUpdate(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statementHandler.addBatch(statement, mappedStatement);
    }

    @Override
    protected int addBatch(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statementHandler.addBatch(statement, mappedStatement);
    }

    @Override
    public int[] flushStatement(boolean rollback) {
        try {
            if (!rollback) {
                for (BatchResult batchResult : batchResultList) {
                    try {
                        statement.executeBatch();
                    } finally {
                        if (batchResult.statement != null && !batchResult.statement.isClosed())
                            batchResult.statement.close();
                    }
                }
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            currentBatchResult = null;
            batchResultList.clear();
        }
        return null;
    }

    @Override
    public void close() {

    }

    public static class BatchResult {
        private CacheKey sqlKey;
        private Statement statement;
        private int paramCount;

        public BatchResult(CacheKey sqlKey, Statement statement, int paramCount) {
            this.sqlKey = sqlKey;
            this.statement = statement;
            this.paramCount = paramCount;
        }
    }
}
