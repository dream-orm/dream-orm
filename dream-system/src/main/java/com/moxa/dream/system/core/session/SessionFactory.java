package com.moxa.dream.system.core.session;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;

public interface SessionFactory {
    default Session openSession() {
        return openSession(false);
    }

    default Session openSession(boolean autoCommit) {
        return openSession(autoCommit, true);
    }

    Session openSession(boolean autoCommit, boolean cache);

    Session openSession(Executor executor);

    Configuration getConfiguration();
}
