package com.moxa.dream.module.config;

import java.io.InputStream;
import java.io.Reader;

public interface ConfigurationFactory {

    void setDefaultConfig(DefaultConfig defaultConfig);

    Configuration getConfiguration();

    Configuration getConfiguration(InputStream inputStream);

    Configuration getConfiguration(Reader reader);
}
