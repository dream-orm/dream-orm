package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;

public interface SessionFactory {
    Session openSession(boolean autoCommit);

    Session openSession(Executor executor);

    Configuration getConfiguration();
}
