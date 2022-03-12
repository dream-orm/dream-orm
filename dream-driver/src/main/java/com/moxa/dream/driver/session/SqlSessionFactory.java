package com.moxa.dream.driver.session;

import com.moxa.dream.module.engine.executor.Executor;
import com.moxa.dream.module.hold.config.Configuration;

public interface SqlSessionFactory {

    SqlSession openSession();

    SqlSession openSession(boolean autoCommit);

    SqlSession openSession(boolean autoCommit, boolean batch);

    SqlSession openSession(boolean autoCommit, boolean batch, boolean enable);

    SqlSession openSession(Executor executor);

    Configuration getConfiguration();

}
