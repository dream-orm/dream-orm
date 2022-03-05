package com.moxa.dream.engine.executor;

import com.moxa.dream.engine.statement.PrepareStatementHandler;
import com.moxa.dream.engine.statement.StatementHandler;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapped.MappedStatement;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, boolean autoCommit) {
        super(configuration, autoCommit);
    }

    @Override
    public StatementHandler createStatementHandler(MappedStatement mappedStatement) {
        return new PrepareStatementHandler();
    }
}
