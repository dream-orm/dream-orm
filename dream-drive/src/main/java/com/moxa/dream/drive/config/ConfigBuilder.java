package com.moxa.dream.drive.config;

import com.moxa.dream.drive.alias.AliasFactory;
import com.moxa.dream.drive.alias.DefaultAliasFactory;
import com.moxa.dream.drive.listener.DefaultListenerFactory;
import com.moxa.dream.drive.mapper.DefaultMapperFactory;
import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.builder.config.ConfigurationBuilder;
import com.moxa.dream.drive.xml.moudle.XmlCallback;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.moudle.XmlParser;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.cache.DefaultCacheFactory;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.compile.DefaultCompileFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.inject.factory.DefaultInjectFactory;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.mapper.MapperFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.plugin.factory.ProxyPluginFactory;
import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.system.table.factory.DefaultTableFactory;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.transaction.factory.JdbcTransactionFactory;
import com.moxa.dream.system.transaction.factory.TransactionFactory;
import com.moxa.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.wrapper.TypeHandlerWrapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.resource.ResourceUtil;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ConfigBuilder {
    private final DefaultConfig defaultConfig;
    private Configuration configuration;
    private AliasFactory aliasFactory;

    public ConfigBuilder() {
        this(null);
    }

    public ConfigBuilder(DefaultConfig defaultConfig) {
        if (defaultConfig == null) {
            defaultConfig = initDefaultConfig();
        }
        this.configuration = new Configuration();
        this.defaultConfig = defaultConfig;
        this.initConfiguration();
    }

    public DefaultConfig initDefaultConfig() {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig
                .setAliasFactory(new DefaultAliasFactory())
                .setCacheFactory(new DefaultCacheFactory())
                .setMapperFactory(new DefaultMapperFactory())
                .setTableFactory(new DefaultTableFactory())
                .setCompileFactory(new DefaultCompileFactory())
                .setInjectFactory(new DefaultInjectFactory())
                .setDialectFactory(new DefaultDialectFactory())
                .setTransactionFactory(new JdbcTransactionFactory())
                .setPluginFactory(new ProxyPluginFactory())
                .setListenerFactory(new DefaultListenerFactory())
                .setTypeHandlerFactory(new DefaultTypeHandlerFactory());
        return defaultConfig;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public void initConfiguration() {
        CacheFactory cacheFactory = defaultConfig.getCacheFactory();
        MapperFactory mapperFactory = defaultConfig.getMapperFactory();
        TableFactory tableFactory = defaultConfig.getTableFactory();
        TypeHandlerFactory typeHandlerFactory = defaultConfig.getTypeHandlerFactory();
        CompileFactory compileFactory = defaultConfig.getCompileFactory();
        InjectFactory injectFactory = defaultConfig.getInjectFactory();
        DialectFactory dialectFactory = defaultConfig.getDialectFactory();
        PluginFactory pluginFactory = defaultConfig.getPluginFactory();
        ListenerFactory listenerFactory = defaultConfig.getListenerFactory();
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
        if (compileFactory != null) {
            configuration.setCompileFactory(compileFactory);
        }
        if (injectFactory != null) {
            configuration.setInjectFactory(injectFactory);
        }
        if (dialectFactory != null) {
            configuration.setDialectFactory(dialectFactory);
        }
        if (pluginFactory != null) {
            configuration.setPluginFactory(pluginFactory);
        }
        if (listenerFactory != null) {
            configuration.setListenerFactory(listenerFactory);
        }
        if (transactionFactory != null) {
            configuration.setTransactionFactory(transactionFactory);
        }
        if (dataSourceFactory != null) {
            configuration.setDataSourceFactory(dataSourceFactory);
        }
        aliasFactory = defaultConfig.getAliasFactory();
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
                ObjectUtil.requireNonNull(tableFactory, "TableFactory未在Configuration注册");
                for (Class classType : resourceAsClass) {
                    tableFactory.addTableInfo(classType);
                }
            }
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
            }
        }
        return this;
    }

    public Configuration build() {
        List<String> mapperPackages = defaultConfig.getMapperPackages();
        if (!ObjectUtil.isNull(mapperPackages)) {
            for (String mapperPackage : mapperPackages) {
                mapperMapping(mapperPackage);
            }
        }
        List<String> tablePackages = defaultConfig.getTablePackages();
        if (!ObjectUtil.isNull(tablePackages)) {
            for (String tablePackage : tablePackages) {
                tableMapping(tablePackage);
            }
        }
        return configuration;
    }

    public Configuration build(InputStream inputStream) {
        if (inputStream != null) {
            XmlParser xmlParser = new XmlParser();
            xmlParser.parse(new InputSource(inputStream), new XmlCallback() {
                @Override
                public XMLBuilder startDocument(XmlHandler xmlHandler) {
                    return new ConfigurationBuilder(xmlHandler, ConfigBuilder.this);
                }

                @Override
                public void endDocument(Object value) {
                    ConfigBuilder.this.configuration = (Configuration) value;
                }
            });
            return configuration;
        } else {
            return build();
        }
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
        ObjectUtil.requireNonNull(cacheFactory, "CacheFactory未在Configuration注册");
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
        if (!ObjectUtil.isNull(typeHandlerWrappers)) {
            TypeHandlerWrapper[] typeHandlerWrapperList = new TypeHandlerWrapper[typeHandlerWrappers.size()];
            for (int i = 0; i < typeHandlerWrappers.size(); i++) {
                Class<? extends TypeHandlerWrapper> typeHandlerWrapperClass = ReflectUtil.loadClass(typeHandlerWrappers.get(i));
                typeHandlerWrapperList[i] = ReflectUtil.create(typeHandlerWrapperClass);
            }
            TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
            ObjectUtil.requireNonNull(typeHandlerFactory, "TypeHandlerFactory未在Configuration注册");
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

    public ConfigBuilder compileFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends CompileFactory> compileFactoryClass = ReflectUtil.loadClass(type);
            CompileFactory compileFactory = ReflectUtil.create(compileFactoryClass);
            configuration.setCompileFactory(compileFactory);
        }
        return this;
    }

    public ConfigBuilder compileProperties(Properties properties) {
        CompileFactory compileFactory = configuration.getCompileFactory();
        ObjectUtil.requireNonNull(compileFactory, "CompileFactory未在Configuration注册");
        compileFactory.setProperties(getProperties(properties));
        return this;
    }

    public ConfigBuilder injectFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends InjectFactory> injectFactoryClass = ReflectUtil.loadClass(type);
            InjectFactory injectFactory = ReflectUtil.create(injectFactoryClass);
            configuration.setInjectFactory(injectFactory);
        }
        return this;
    }

    public ConfigBuilder injects(List<String> injectList) {
        if (!ObjectUtil.isNull(injectList)) {
            Inject[] injects = new Inject[injectList.size()];
            InjectFactory injectFactory = configuration.getInjectFactory();
            ObjectUtil.requireNonNull(injectFactory, "InjectFactory未在Configuration注册");
            for (int i = 0; i < injectList.size(); i++) {
                String value = injectList.get(i);
                Class<? extends Inject> injectClass = ReflectUtil.loadClass(value);
                injects[i] = ReflectUtil.create(injectClass);
            }
            injectFactory.injects(injects);
        }
        return this;
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

    public ConfigBuilder dialectProperties(Properties properties) {
        DialectFactory dialectFactory = configuration.getDialectFactory();
        ObjectUtil.requireNonNull(dialectFactory, "DialectFactory未在Configuration注册");
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
            ObjectUtil.requireNonNull(pluginFactory, "PluginFactory未在Configuration注册");
            for (int i = 0; i < interceptorList.size(); i++) {
                String value = interceptorList.get(i);
                Class<? extends Interceptor> interceptorClass = ReflectUtil.loadClass(value);
                interceptors[i] = ReflectUtil.create(interceptorClass);
            }
            pluginFactory.interceptor(interceptors);
        }
        return this;
    }

    public ConfigBuilder listenerFactory(String type) {
        if (!ObjectUtil.isNull(type)) {
            type = getValue(type);
            Class<? extends ListenerFactory> listenerFactoryClass = ReflectUtil.loadClass(type);
            ListenerFactory listenerFactory = ReflectUtil.create(listenerFactoryClass);
            configuration.setListenerFactory(listenerFactory);
        }
        return this;
    }

    public ConfigBuilder listener(List<String> listenerList) {
        if (!ObjectUtil.isNull(listenerList)) {
            Listener[] listeners = new Listener[listenerList.size()];
            ListenerFactory listenerFactory = configuration.getListenerFactory();
            ObjectUtil.requireNonNull(listenerFactory, "ListenerFactory未在Configuration注册");
            for (int i = 0; i < listenerList.size(); i++) {
                String value = listenerList.get(i);
                Class<? extends Listener> listenerClass = ReflectUtil.loadClass(value);
                listeners[i] = ReflectUtil.create(listenerClass);
            }
            listenerFactory.listener(listeners);
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
        ObjectUtil.requireNonNull(transactionFactory, "TransactionFactory未在Configuration注册");
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
        ObjectUtil.requireNonNull(dataSourceFactory, "DataSourceFactory未在Configuration注册");
        dataSourceFactory.setProperties(getProperties(properties));
        return this;
    }
}
