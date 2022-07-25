package com.moxa.dream.driver.config;

import com.moxa.dream.driver.alias.DefaultAliasFactory;
import com.moxa.dream.driver.dialect.MySQLDialectFactory;
import com.moxa.dream.driver.factory.DefaultListenerFactory;
import com.moxa.dream.driver.factory.DefaultMapperFactory;
import com.moxa.dream.system.cache.factory.MemoryCacheFactory;
import com.moxa.dream.system.plugin.factory.JavaPluginFactory;
import com.moxa.dream.system.table.factory.DefaultTableFactory;
import com.moxa.dream.system.transaction.factory.JdbcTransactionFactory;
import com.moxa.dream.system.typehandler.factory.DefaultTypeHandlerFactory;

import java.util.List;

public class ConfigUtil {
    public static DefaultConfig getDefaultConfig(List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setAliasFactory(new DefaultAliasFactory())
                .setCacheFactory(new MemoryCacheFactory())
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory())
                .setDialectFactory(new MySQLDialectFactory())
                .setTransactionFactory(new JdbcTransactionFactory())
                .setPluginFactory(new JavaPluginFactory())
                .setListenerFactory(new DefaultListenerFactory())
                .setTypeHandlerFactory(new DefaultTypeHandlerFactory())
                .setTablePackages(tablePackages)
                .setMapperPackages(mapperPackages);
        return defaultConfig;
    }
}
