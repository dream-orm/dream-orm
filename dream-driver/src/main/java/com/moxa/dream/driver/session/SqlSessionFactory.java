package com.moxa.dream.driver.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.transaction.Transaction;

public interface SqlSessionFactory {
    SqlSession openSession();

    SqlSession openSession(Control control);

    SqlSession openSession(Executor executor);

    Configuration getConfiguration();

}
