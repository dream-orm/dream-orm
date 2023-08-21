package com.dream.boot.build;

import com.dream.system.config.Configuration;
import com.dream.system.core.session.SessionFactory;

public interface SessionFactoryBuilder {
    SessionFactory build(Configuration configuration);
}