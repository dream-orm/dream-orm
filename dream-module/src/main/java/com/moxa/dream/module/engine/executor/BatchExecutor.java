package com.moxa.dream.module.engine.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.engine.statement.BatchStatementHandler;
import com.moxa.dream.module.engine.statement.StatementHandler;

public class BatchExecutor extends AbstractExecutor {
    private Executor executor;

    public BatchExecutor(Executor executor, Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
        this.executor = executor;
    }

    @Override
    protected StatementHandler createStatementHandler() {
        return new BatchStatementHandler(executor.getStatementHandler());
    }

    @Override
    public void commit() {
        try {
            flushStatement(true);
        } finally {
            super.commit();
        }
    }

    @Override
    public void rollback() {
        try {
            flushStatement(false);
        } finally {
            super.rollback();
        }
    }

    @Override
    public void close() {
        try {
            flushStatement(false);
        } finally {
            super.close();
        }
    }

    public int[] flushStatement(boolean rollback) {
        if (statementHandler != null)
            return statementHandler.flushStatement(rollback);
        else
            return null;
    }
}
