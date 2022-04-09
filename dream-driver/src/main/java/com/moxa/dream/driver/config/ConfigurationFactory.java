package com.moxa.dream.driver.config;

import com.moxa.dream.system.config.Configuration;

import java.io.InputStream;
import java.io.Reader;

public interface ConfigurationFactory {

    void setDefaultConfig(DefaultConfig defaultConfig);

    Configuration getConfiguration();

    Configuration getConfiguration(InputStream inputStream);

    Configuration getConfiguration(Reader reader);
}
