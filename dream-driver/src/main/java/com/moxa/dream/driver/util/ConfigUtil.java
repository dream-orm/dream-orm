package com.moxa.dream.driver.util;

import com.moxa.dream.driver.alias.DefaultAliasFactory;
import com.moxa.dream.module.cache.DefaultCacheFactory;
import com.moxa.dream.module.config.DefaultConfig;
import com.moxa.dream.module.engine.type.factory.BaseTypeHandlerFactory;
import com.moxa.dream.module.mapper.DefaultMapperFactory;
import com.moxa.dream.module.table.DefaultTableFactory;

public class ConfigUtil {

    public static DefaultConfig getDefaultConfig() {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setCacheFactory(new DefaultCacheFactory())
                .setAliasFactory(new DefaultAliasFactory())
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory())
                .setTypeHandlerFactory(new BaseTypeHandlerFactory());
        return defaultConfig;
    }

    public static String wrap$(String name) {
        return "@$:dream(" + name + ")";
    }
}
