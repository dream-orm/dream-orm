package com.moxa.dream.driver.session;

import com.moxa.dream.driver.util.ConfigUtil;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.config.ConfigurationFactory;
import com.moxa.dream.module.config.DefaultConfig;
import com.moxa.dream.module.config.DefaultConfigurationFactory;

import java.io.InputStream;
import java.io.Reader;

public class SqlSessionFactoryBuilder {
    private ConfigurationFactory configurationFactory = new DefaultConfigurationFactory();

    public SqlSessionFactory build(InputStream inputStream) {
        return build(inputStream, ConfigUtil.getDefaultConfig());
    }

    public SqlSessionFactory build(InputStream inputStream, DefaultConfig defaultConfig) {
        configurationFactory.setDefaultConfig(defaultConfig);
        Configuration configuration = configurationFactory.getConfiguration(inputStream);
        return build(configuration);
    }

    public SqlSessionFactory build(Reader reader) {
        return build(reader, ConfigUtil.getDefaultConfig());
    }

    public SqlSessionFactory build(Reader reader, DefaultConfig defaultConfig) {
        configurationFactory.setDefaultConfig(defaultConfig);
        Configuration configuration = configurationFactory.getConfiguration(reader);
        return build(configuration);

    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}
