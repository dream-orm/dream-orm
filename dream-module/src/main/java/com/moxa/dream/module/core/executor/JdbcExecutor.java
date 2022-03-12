package com.moxa.dream.module.core.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.core.statementhandler.PrepareStatementHandler;
import com.moxa.dream.module.core.statementhandler.StatementHandler;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler() {
        return new PrepareStatementHandler();
    }
}
