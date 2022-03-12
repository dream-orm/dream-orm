package com.moxa.dream.driver.config;

import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.driver.alias.AliasFactory;
import com.moxa.dream.module.cache.CacheFactory;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.datasource.DataSourceFactory;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.mapper.factory.MapperFactory;
import com.moxa.dream.module.plugin.factory.PluginFactory;
import com.moxa.dream.module.plugin.interceptor.Interceptor;
import com.moxa.dream.module.table.factory.TableFactory;
import com.moxa.dream.module.transaction.factory.TransactionFactory;
import com.moxa.dream.module.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.module.typehandler.wrapper.TypeHandlerWrapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.resource.ResourceUtil;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigBuilder {
    private Configuration configuration;
    private AliasFactory aliasFactory;
    private DefaultConfig defaultConfig;
    private boolean addMapperPackage = false;
    private boolean addTablePackage = false;
    private boolean addDialect = false;

    public ConfigBuilder(DefaultConfig defaultConfig) {
        this.configuration = new Configuration();
        this.defaultConfig = defaultConfig;
        init();
    }

    private void init() {
        if (defaultConfig != null) {
            CacheFactory cacheFactory = defaultConfig.getCacheFactory();
            MapperFactory mapperFactory = defaultConfig.getMapperFactory();
            TableFactory tableFactory = defaultConfig.getTableFactory();
            TypeHandlerFactory typeHandlerFactory = defaultConfig.getTypeHandlerFactory();
            DialectFactory dialectFactory = defaultConfig.getDialectFactory();
            TransactionFactory transactionFactory = defaultConfig.getTransactionFactory();
            DataSourceFactory dataSourceFactory = defaultConfig.getDataSourceFactory();
            if (cacheFactory != null) {
                configuration.setCacheFactory(cacheFactory);
            }
            if (mapperFactory != null) {
                configuration.setMapperFactory(mapperFactory);
            }
            if (tableFactory != null) {
                configuration.setTableFactory(tableFactory);
            }
            if (typeHandlerFactory != null) {
                configuration.setTypeHandlerFactory(typeHandlerFactory);
            }
            if (dialectFactory != null) {
                configuration.setDialectFactory(dialectFactory);
            }
            if (transactionFactory != null) {
                configuration.setTransactionFactory(transactionFactory);
            }
            if (dataSourceFactory != null) {
                configuration.setDataSourceFactory(dataSourceFactory);
            }
            aliasFactory = defaultConfig.getAliasFactory();
        }
    }

    private String getValue(String value) {
        if (aliasFactory == null)
            return value;
        else
            return aliasFactory.getValue(value);
    }

    public ConfigBuilder aliasFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            Class<? extends AliasFactory> aliasFactoryClass = ReflectUtil.loadClass(type);
            aliasFactory = ReflectUtil.create(aliasFactoryClass);
        }
        return this;
    }

    public ConfigBuilder aliasProperties(Properties properties) {
        if (aliasFactory != null) {
            aliasFactory.setProperties(getProperties(properties));
        }
        return this;
    }

    public ConfigBuilder tableFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<TableFactory> tableRuleFactoryClass = ReflectUtil.loadClass(type);
            TableFactory tableFactory = ReflectUtil.create(tableRuleFactoryClass);
            configuration.setTableFactory(tableFactory);
        }
        return this;
    }

    public ConfigBuilder tableMapping(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            type = type.replace(".", "/");
            List<Class> resourceAsClass = ResourceUtil.getResourceAsClass(type);
            if (!ObjectUtil.isNull(resourceAsClass)) {
                TableFactory tableFactory = configuration.getTableFactory();
                ObjectUtil.requireNonNull(tableFactory, "Property 'tableFactory' is required");
                for (Class classType : resourceAsClass) {
                    tableFactory.addTableInfo(classType);
                }
            }
            addTablePackage = true;
        }
        return this;
    }

    public ConfigBuilder mapperFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends MapperFactory> mapperRuleFactoryClass = ReflectUtil.loadClass(type);
            MapperFactory mapperFactory = ReflectUtil.create(mapperRuleFactoryClass);
            configuration.setMapperFactory(mapperFactory);
        }
        return this;
    }

    public ConfigBuilder mapperMapping(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            String resourcePath = type.replace(".", "/");
            List<Class> resourceAsClass = ResourceUtil.getResourceAsClass(resourcePath);
            if (!ObjectUtil.isNull(resourceAsClass)) {
                for (Class classType : resourceAsClass) {
                    configuration.addMapper(classType);
                }
                addMapperPackage = true;
            }
        }
        return this;
    }

    public Configuration builder() {
        List<String> mapperPackages = defaultConfig.getMapperPackages();
        if (!addMapperPackage && !ObjectUtil.isNull(mapperPackages)) {
            for (String mapperPackage : mapperPackages) {
                mapperMapping(mapperPackage);
            }
        }
        List<String> tablePackages = defaultConfig.getTablePackages();
        if (!addTablePackage && !ObjectUtil.isNull(tablePackages)) {
            for (String tablePackage : tablePackages) {
                tableMapping(tablePackage);
            }
        }
        String dialect = defaultConfig.getDialect();
        if (!addDialect && !ObjectUtil.isNull(dialect)) {
            dialectToSQL(dialect);
        }
        return configuration;
    }

    public ConfigBuilder cacheFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends CacheFactory> cacheFactoryClass = ReflectUtil.loadClass(type);
            CacheFactory cacheFactory = ReflectUtil.create(cacheFactoryClass);
            configuration.setCacheFactory(cacheFactory);
        }
        return this;
    }

    public ConfigBuilder cacheProperties(Properties properties) {
        CacheFactory cacheFactory = configuration.getCacheFactory();
        ObjectUtil.requireNonNull(cacheFactory, "Property 'cacheFactory' is required");
        cacheFactory.setProperties(getProperties(properties));
        return this;
    }

    public ConfigBuilder typeHandlerFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends TypeHandlerFactory> typeHandlerWrapperFactoryClass = ReflectUtil.loadClass(type);
            TypeHandlerFactory typeHandlerWrapperFactory = ReflectUtil.create(typeHandlerWrapperFactoryClass);
            configuration.setTypeHandlerFactory(typeHandlerWrapperFactory);
        }
        return this;
    }

    public ConfigBuilder typeHandlerWrapperList(List<String> typeHandlerWrappers) {
        if (ObjectUtil.isNull(typeHandlerWrappers)) {
            List<? extends TypeHandlerWrapper> typeHandlerWrapperList = typeHandlerWrappers.stream().map(typeHandlerWrapper -> {
                Class<? extends TypeHandlerWrapper> typeHandlerWrapperClass = ReflectUtil.loadClass(typeHandlerWrapper);
                return ReflectUtil.create(typeHandlerWrapperClass);
            }).collect(Collectors.toList());
            TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
            ObjectUtil.requireNonNull(typeHandlerFactory, "Property 'typeHandlerFactory' is required");
            typeHandlerFactory.wrapper(typeHandlerWrapperList);
        }
        return this;
    }

    private Properties getProperties(Properties properties) {
        if (!ObjectUtil.isNull(properties)) {
            properties.forEach((k, v) -> {
                if (v != null && v instanceof String)
                    properties.put(k, getValue((String) v));
            });
        }
        return properties;
    }

    public ConfigBuilder dialectFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends DialectFactory> dialectFactoryClass = ReflectUtil.loadClass(type);
            DialectFactory dialectFactory = ReflectUtil.create(dialectFactoryClass);
            configuration.setDialectFactory(dialectFactory);
        }
        return this;
    }

    public ConfigBuilder dialectToSQL(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends ToSQL> toSQLClass = ReflectUtil.loadClass(type);
            ToSQL toSQL = ReflectUtil.create(toSQLClass);
            DialectFactory dialectFactory = configuration.getDialectFactory();
            ObjectUtil.requireNonNull(dialectFactory, "Property 'dialectFactory' is required");
            dialectFactory.setDialect(toSQL);
            addDialect = true;
        }
        return this;
    }

    public ConfigBuilder dialectProperties(Properties properties) {
        DialectFactory dialectFactory = configuration.getDialectFactory();
        ObjectUtil.requireNonNull(dialectFactory, "Property 'dialectFactory' is required");
        dialectFactory.setProperties(getProperties(properties));
        return this;
    }

    public ConfigBuilder pluginFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends PluginFactory> pluginFactoryClass = ReflectUtil.loadClass(type);
            PluginFactory pluginFactory = ReflectUtil.create(pluginFactoryClass);
            configuration.setPluginFactory(pluginFactory);
        }
        return this;
    }

    public ConfigBuilder interceptor(List<String> interceptorList) {
        if (!ObjectUtil.isNull(interceptorList)) {
            Interceptor[] interceptors = new Interceptor[interceptorList.size()];
            PluginFactory pluginFactory = configuration.getPluginFactory();
            ObjectUtil.requireNonNull(pluginFactory, "Property 'pluginFactory' is required");
            for (int i = 0; i < interceptorList.size(); i++) {
                String value = interceptorList.get(i);
                Class<? extends Interceptor> interceptorClass = ReflectUtil.loadClass(value);
                interceptors[i] = ReflectUtil.create(interceptorClass);
                pluginFactory.interceptor(interceptors);
            }
        }
        return this;
    }

    public ConfigBuilder transactionFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends TransactionFactory> transactionFactoryClass = ReflectUtil.loadClass(type);
            TransactionFactory transactionFactory = ReflectUtil.create(transactionFactoryClass);
            configuration.setTransactionFactory(transactionFactory);
        }
        return this;
    }

    public ConfigBuilder transactionProperties(Properties properties) {
        TransactionFactory transactionFactory = configuration.getTransactionFactory();
        ObjectUtil.requireNonNull(transactionFactory, "Property 'transactionFactory' is required");
        transactionFactory.setProperties(getProperties(properties));
        return this;
    }

    public ConfigBuilder dataSourceFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends DataSourceFactory> dataSourceFactoryClass = ReflectUtil.loadClass(type);
            DataSourceFactory dataSourceFactory = ReflectUtil.create(dataSourceFactoryClass);
            configuration.setDataSourceFactory(dataSourceFactory);
        }
        return this;
    }

    public ConfigBuilder dataSourceProperties(Properties properties) {
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        ObjectUtil.requireNonNull(dataSourceFactory, "Property 'dataSourceFactory' is required");
        dataSourceFactory.setProperties(getProperties(properties));
        return this;
    }
}