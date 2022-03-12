package com.moxa.dream.driver.util;

import com.moxa.dream.driver.alias.DefaultAliasFactory;
import com.moxa.dream.driver.config.DefaultConfig;
import com.moxa.dream.driver.factory.DefaultMapperFactory;
import com.moxa.dream.module.cache.DefaultCacheFactory;
import com.moxa.dream.module.table.factory.DefaultTableFactory;
import com.moxa.dream.module.typehandler.factory.BaseTypeHandlerFactory;

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
