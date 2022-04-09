package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.statementhandler.PrepareStatementHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler() {
        return new PrepareStatementHandler();
    }
}
