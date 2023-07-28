package com.moxa.dream.solon.build;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.SessionFactory;

public interface SessionFactoryBuilder {
    SessionFactory build(Configuration configuration);
}
