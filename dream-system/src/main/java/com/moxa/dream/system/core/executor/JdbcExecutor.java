package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.statementhandler.PrepareStatementHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.transaction.Transaction;

public class JdbcExecutor extends AbstractExecutor {


    public JdbcExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    public StatementHandler getStatementHandler() {
        return new PrepareStatementHandler();
    }
}
