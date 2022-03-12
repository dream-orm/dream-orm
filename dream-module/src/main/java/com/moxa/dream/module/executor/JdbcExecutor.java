package com.moxa.dream.module.executor;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.statementhandler.PrepareStatementHandler;
import com.moxa.dream.module.statementhandler.StatementHandler;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler() {
        return new PrepareStatementHandler();
    }
}
