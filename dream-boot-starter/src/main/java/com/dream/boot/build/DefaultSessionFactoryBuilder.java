package com.dream.boot.build;

import com.dream.system.config.Configuration;
import com.dream.system.core.session.DefaultSessionFactory;
import com.dream.system.core.session.SessionFactory;

public class DefaultSessionFactoryBuilder implements SessionFactoryBuilder {
    @Override
    public SessionFactory build(Configuration configuration) {
        return new DefaultSessionFactory(configuration);
    }
}