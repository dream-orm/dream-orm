package com.moxa.dream.driver.config;

import com.moxa.dream.driver.alias.DefaultAliasFactory;
import com.moxa.dream.driver.factory.DefaultDialectFactory;
import com.moxa.dream.driver.factory.DefaultListenerFactory;
import com.moxa.dream.driver.factory.DefaultMapperFactory;
import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.builder.config.ConfigurationBuilder;
import com.moxa.dream.driver.xml.moudle.XmlCallback;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.moudle.XmlParser;
import com.moxa.dream.system.cache.factory.DefaultCacheFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.plugin.factory.JavaPluginFactory;
import com.moxa.dream.system.table.factory.DefaultTableFactory;
import com.moxa.dream.system.transaction.factory.JdbcTransactionFactory;
import com.moxa.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.moxa.dream.util.common.ObjectUtil;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

public class DefaultConfigurationFactory implements ConfigurationFactory {
    private DefaultConfig defaultConfig;

    @Override
    public void setDefaultConfig(DefaultConfig defaultConfig) {
        if (defaultConfig == null) {
            defaultConfig = new DefaultConfig();
            defaultConfig
                    .setAliasFactory(new DefaultAliasFactory())
                    .setCacheFactory(new DefaultCacheFactory())
                    .setMapperFactory(new DefaultMapperFactory())
                    .setTableFactory(new DefaultTableFactory())
                    .setDialectFactory(new DefaultDialectFactory())
                    .setTransactionFactory(new JdbcTransactionFactory())
                    .setPluginFactory(new JavaPluginFactory())
                    .setListenerFactory(new DefaultListenerFactory())
                    .setTypeHandlerFactory(new DefaultTypeHandlerFactory());
        }
        this.defaultConfig = defaultConfig;
    }

    @Override
    public Configuration getConfiguration() {
        return new ConfigBuilder(defaultConfig).builder();
    }

    @Override
    public Configuration getConfiguration(InputStream inputStream) {
        ObjectUtil.requireNonNull(inputStream, "Property 'inputStream' is required");
        return getConfiguration(new InputSource(inputStream));
    }

    @Override
    public Configuration getConfiguration(Reader reader) {
        ObjectUtil.requireNonNull(reader, "Property 'reader' is required");
        return getConfiguration(new InputSource(reader));
    }

    public Configuration getConfiguration(InputSource inputSource) {
        ObjectUtil.requireNonNull(inputSource, "Property 'inputSource' is required");
        XmlParser xmlParser = new XmlParser();
        final Configuration[] configuration = new Configuration[1];
        xmlParser.parse(inputSource, new XmlCallback() {
            @Override
            public XMLBuilder startDocument(XmlHandler xmlHandler) {
                return new ConfigurationBuilder(xmlHandler, defaultConfig);
            }

            @Override
            public void endDocument(Object value) {
                configuration[0] = (Configuration) value;
            }
        });
        return configuration[0];
    }
}
