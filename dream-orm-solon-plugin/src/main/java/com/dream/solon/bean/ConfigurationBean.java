package com.dream.solon.bean;

import com.dream.drive.config.DefaultConfig;
import com.dream.system.config.Configuration;
import com.dream.system.mapper.DefaultMapperFactory;
import com.dream.system.table.factory.DefaultTableFactory;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationBean {
    private List<String> tablePackages = new ArrayList<>();
    private List<String> mapperPackages = new ArrayList<>();

    public ConfigurationBean() {
        this(null, null);
    }

    public ConfigurationBean(List<String> tablePackages, List<String> mapperPackages) {
        this.addTablePackages(tablePackages);
        this.addMapperPackages(mapperPackages);
    }

    public void addTablePackages(List<String> tablePackages) {
        if (tablePackages != null) {
            this.tablePackages.addAll(tablePackages);
        }
    }

    public void addMapperPackages(List<String> mapperPackages) {
        if (mapperPackages != null) {
            this.mapperPackages.addAll(mapperPackages);
        }
    }

    public Configuration getObject() {
        DefaultConfig defaultConfig = defaultConfig(this.tablePackages, this.mapperPackages);
        return defaultConfig.toConfiguration();
    }


    protected DefaultConfig defaultConfig(List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory());
        defaultConfig.setTablePackages(tablePackages);
        defaultConfig.setMapperPackages(mapperPackages);
        return defaultConfig;
    }
}

