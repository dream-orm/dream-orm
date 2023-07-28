package com.moxa.dream.solon.build;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.DefaultSessionFactory;
import com.moxa.dream.system.core.session.SessionFactory;

public class DefaultSessionFactoryBuilder implements SessionFactoryBuilder {
    @Override
    public SessionFactory build(Configuration configuration) {
        return new DefaultSessionFactory(configuration);
    }
}
