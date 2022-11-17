package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.transaction.Transaction;

public class JdbcExecutor extends AbstractExecutor {

    public JdbcExecutor(Configuration configuration, Transaction transaction, StatementHandler statementHandler, ResultSetHandler resultSetHandler, SessionFactory sessionFactory) {
        super(configuration, transaction, statementHandler, resultSetHandler, sessionFactory);
    }
}
