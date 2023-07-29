package com.moxa.dream.solon.bean;

import com.moxa.dream.drive.config.DefaultConfig;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.DefaultMapperFactory;
import com.moxa.dream.system.table.factory.DefaultTableFactory;

import java.util.List;

public class ConfigurationBean {
    private DefaultConfig defaultConfig;

    public ConfigurationBean(List<String> tablePackages, List<String> mapperPackages) {
        this.defaultConfig = this.initDefaultConfig(tablePackages, mapperPackages);
    }

    public Configuration getObject() {
        return defaultConfig.toConfiguration();
    }

    protected DefaultConfig initDefaultConfig(List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory());
        defaultConfig.setTablePackages(tablePackages);
        defaultConfig.setMapperPackages(mapperPackages);
        return defaultConfig;
    }
}
