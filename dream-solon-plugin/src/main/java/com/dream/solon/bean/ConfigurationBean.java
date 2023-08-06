package com.dream.solon.bean;

import com.dream.drive.config.DefaultConfig;
import com.dream.system.config.Configuration;
import com.dream.system.mapper.DefaultMapperFactory;
import com.dream.system.table.factory.DefaultTableFactory;

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
