package com.moxa.dream.module.engine.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.engine.statement.PrepareStatementHandler;
import com.moxa.dream.module.engine.statement.StatementHandler;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler() {
        return new PrepareStatementHandler();
    }
}
