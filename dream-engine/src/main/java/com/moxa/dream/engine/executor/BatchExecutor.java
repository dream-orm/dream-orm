package com.moxa.dream.engine.executor;

import com.moxa.dream.engine.statement.BatchStatementHandler;
import com.moxa.dream.engine.statement.StatementHandler;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.SQLException;

public class BatchExecutor extends AbstractExecutor {
    private Executor executor;

    public BatchExecutor(Executor executor, Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
        this.executor = executor;
    }

    @Override
    protected StatementHandler createStatementHandler(MappedStatement mappedStatement) {
        return new BatchStatementHandler(executor.getStatementHandler(mappedStatement));
    }

    @Override
    public void commit() throws SQLException {
        try {
            flushStatement(true);
        } finally {
            super.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        try {
            flushStatement(false);
        } finally {
            super.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            flushStatement(false);
        } finally {
            super.close();
        }
    }

    public int[] flushStatement(boolean rollback) throws SQLException {
        if (statementHandler != null)
            return statementHandler.flushStatement(rollback);
        else
            return null;
    }
}
