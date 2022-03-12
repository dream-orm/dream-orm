package com.moxa.dream.module.engine.executor;

import com.moxa.dream.module.engine.statement.PrepareStatementHandler;
import com.moxa.dream.module.engine.statement.StatementHandler;
import com.moxa.dream.module.hold.config.Configuration;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler() {
        return new PrepareStatementHandler();
    }
}
