package com.moxa.dream.driver.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;

public interface SqlSessionFactory {

    SqlSession openSession();

    SqlSession openSession(boolean autoCommit);

    SqlSession openSession(boolean autoCommit, boolean batch);

    SqlSession openSession(boolean autoCommit, boolean batch, boolean enable);

    SqlSession openSession(Executor executor);

    Configuration getConfiguration();

}
