package com.moxa.dream.module.xml.builder.config;

import com.moxa.dream.module.config.DefaultConfig;
import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigurationBuilder extends XMLBuilder {
    private ConfigBuilder configBuilder;
    private AliasFactoryBuilder.AliasFactory aliasFactory;
    private TableFactoryBuilder.TableFactory tableFactory;
    private MapperFactoryBuilder.MapperFactory mapperFactory;
    private DialectFactoryBuilder.DialectFactory dialectFactory;
    private CacheFactoryBuilder.CacheFactory cacheFactory;
    private TypeHandlerFactoryBuilder.TypeHandlerFactory typeHandlerFactory;
    private PluginFactoryBuilder.PluginFactory pluginFactory;
    private TransactionFactoryBuilder.TransactionFactory transactionFactory;
    private DataSourceFactoryBuilder.DataSourceFactory dataSourceFactory;
    private DefaultConfig defaultConfig;

    public ConfigurationBuilder(XmlHandler xmlHandler, DefaultConfig defaultConfig) {
        super(xmlHandler);
        this.defaultConfig = defaultConfig;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.CONFIGURATION:
                configBuilder = new ConfigBuilder(defaultConfig);
                break;
            case XmlConstant.ALIASFACTORY:
                AliasFactoryBuilder aliasFactoryBuilder = new AliasFactoryBuilder(workHandler);
                aliasFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.TABLEFACTORY:
                TableFactoryBuilder tableFactoryBuilder = new TableFactoryBuilder(workHandler);
                tableFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.MAPPERFACTORY:
                MapperFactoryBuilder mapperFactoryBuilder = new MapperFactoryBuilder(workHandler);
                mapperFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.CACHEFACTORY:
                CacheFactoryBuilder cacheFactoryBuilder = new CacheFactoryBuilder(workHandler);
                cacheFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.TYPEHANDLERFACTORY:
                TypeHandlerFactoryBuilder typeHandlerFactoryBuilder = new TypeHandlerFactoryBuilder(workHandler);
                typeHandlerFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.DIALECTFACTORY:
                DialectFactoryBuilder dialectFactoryBuilder = new DialectFactoryBuilder(workHandler);
                dialectFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.PLUGINFACTORY:
                PluginFactoryBuilder pluginFactoryBuilder = new PluginFactoryBuilder(workHandler);
                pluginFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.TRANSACTIONFACTORY:
                TransactionFactoryBuilder transactionFactoryBuilder = new TransactionFactoryBuilder(workHandler);
                transactionFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.DATASOURCEFACTORY:
                DataSourceFactoryBuilder dataSourceFactoryBuilder = new DataSourceFactoryBuilder(workHandler);
                dataSourceFactoryBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.CONFIGURATION);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        if (aliasFactory != null) {
            configBuilder.aliasFactory(aliasFactory.getType());
            configBuilder.aliasProperties(getProperties(aliasFactory.getPropertyList()));
        }
        if (dialectFactory != null) {
            configBuilder.dialectFactory(dialectFactory.getType());
            configBuilder.dialectToSQL(dialectFactory.getDialect());
            configBuilder.dialectProperties(getProperties(dialectFactory.getPropertyList()));
        }
        if (tableFactory != null) {
            configBuilder.tableFactory(tableFactory.getType());
            for (MappingBuilder.Mapping mapping : tableFactory.getMappingList()) {
                configBuilder.tableMapping(mapping.getType());
            }
        }
        if (mapperFactory != null) {
            configBuilder.mapperFactory(mapperFactory.getType());
            List<MappingBuilder.Mapping> mappingList = mapperFactory.getMappingList();
            if (!ObjectUtil.isNull(mappingList)) {
                for (MappingBuilder.Mapping mapping : mapperFactory.getMappingList()) {
                    configBuilder.mapperMapping(mapping.getType());
                }
            }
        }
        if (cacheFactory != null) {
            configBuilder.cacheFactory(cacheFactory.getType());
            configBuilder.cacheProperties(getProperties(cacheFactory.getPropertyList()));
        }
        if (typeHandlerFactory != null) {
            configBuilder.typeHandlerFactory(typeHandlerFactory.getType());
            configBuilder.typeHandlerWrapperList(typeHandlerFactory.getTypeHandlerWrapperList().stream().map(typeHandlerWrapper -> typeHandlerWrapper.getType()).collect(Collectors.toList()));
        }
        if (pluginFactory != null) {
            configBuilder.pluginFactory(pluginFactory.getType());
            configBuilder.interceptor(pluginFactory.getInterceptorList().stream().map(interceptor -> interceptor.getType()).collect(Collectors.toList()));
        }
        if (transactionFactory != null) {
            configBuilder.transactionFactory(transactionFactory.getType());
            configBuilder.transactionProperties(getProperties(transactionFactory.getPropertyList()));
        }
        if (dataSourceFactory != null) {
            configBuilder.dataSourceFactory(dataSourceFactory.getType());
            configBuilder.dataSourceProperties(getProperties(dataSourceFactory.getPropertyList()));
        }
        ObjectWrapper.clear();
        return configBuilder.builder();
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.ALIASFACTORY:
                aliasFactory = (AliasFactoryBuilder.AliasFactory) obj;
                break;
            case XmlConstant.TABLEFACTORY:
                tableFactory = (TableFactoryBuilder.TableFactory) obj;
                break;
            case XmlConstant.MAPPERFACTORY:
                mapperFactory = (MapperFactoryBuilder.MapperFactory) obj;
                break;
            case XmlConstant.CACHEFACTORY:
                cacheFactory = (CacheFactoryBuilder.CacheFactory) obj;
                break;
            case XmlConstant.TYPEHANDLERFACTORY:
                typeHandlerFactory = (TypeHandlerFactoryBuilder.TypeHandlerFactory) obj;
                break;
            case XmlConstant.DIALECTFACTORY:
                dialectFactory = (DialectFactoryBuilder.DialectFactory) obj;
                break;
            case XmlConstant.PLUGINFACTORY:
                pluginFactory = (PluginFactoryBuilder.PluginFactory) obj;
                break;
            case XmlConstant.TRANSACTIONFACTORY:
                transactionFactory = (TransactionFactoryBuilder.TransactionFactory) obj;
                break;
            case XmlConstant.DATASOURCEFACTORY:
                dataSourceFactory = (DataSourceFactoryBuilder.DataSourceFactory) obj;
                break;
        }
    }

    Properties getProperties(List<PropertyBuilder.Property> propertyList) {
        Properties properties = null;
        if (propertyList != null && propertyList.size() > 0) {
            properties = new Properties();
            for (PropertyBuilder.Property property : propertyList) {
                properties.put(property.getName(), property.getValue());
            }
        }
        return properties;
    }
}
