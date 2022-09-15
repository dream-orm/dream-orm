package com.moxa.dream.drive.xml.builder.config;

import com.moxa.dream.drive.config.ConfigBuilder;
import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.util.common.ObjectUtil;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigurationBuilder extends XMLBuilder {
    private final ConfigBuilder configBuilder;
    private AliasFactoryBuilder.AliasFactory aliasFactory;
    private TableFactoryBuilder.TableFactory tableFactory;
    private MapperFactoryBuilder.MapperFactory mapperFactory;
    private CompileFactoryBuilder.CompileFactory compileFactory;
    private InjectFactoryBuilder.InjectFactory injectFactory;
    private DialectFactoryBuilder.DialectFactory dialectFactory;
    private CacheFactoryBuilder.CacheFactory cacheFactory;
    private TypeHandlerFactoryBuilder.TypeHandlerFactory typeHandlerFactory;
    private PluginFactoryBuilder.PluginFactory pluginFactory;
    private ListenerFactoryBuilder.ListenerFactory listenerFactory;
    private TransactionFactoryBuilder.TransactionFactory transactionFactory;
    private DataSourceFactoryBuilder.DataSourceFactory dataSourceFactory;

    public ConfigurationBuilder(XmlHandler xmlHandler, ConfigBuilder configBuilder) {
        super(xmlHandler);
        this.configBuilder = configBuilder;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.CONFIGURATION:
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
            case XmlConstant.LISTENERFACTORY:
                ListenerFactoryBuilder listenerFactoryBuilder = new ListenerFactoryBuilder(workHandler);
                listenerFactoryBuilder.startElement(uri, localName, qName, attributes);
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
            List<PropertyBuilder.Property> propertyList = aliasFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                Properties properties = getProperties(propertyList);
                configBuilder.aliasProperties(properties);
            }
        }
        if (compileFactory != null) {
            configBuilder.compileFactory(compileFactory.getType());
            List<PropertyBuilder.Property> propertyList = compileFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                Properties properties = getProperties(propertyList);
                configBuilder.compileProperties(properties);
            }
        }
        if (injectFactory != null) {
            configBuilder.injectFactory(injectFactory.getType());
            List<InjectBuilder.Inject> injectList = injectFactory.getInjectList();
            if (!ObjectUtil.isNull(injectList)) {
                configBuilder.injects(injectList.stream().map(inject -> inject.getType())
                        .collect(Collectors.toList()));
            }
        }
        if (dialectFactory != null) {
            configBuilder.dialectFactory(dialectFactory.getType());
            List<PropertyBuilder.Property> propertyList = dialectFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                Properties properties = getProperties(propertyList);
                configBuilder.dialectProperties(properties);
            }
        }
        if (tableFactory != null) {
            configBuilder.tableFactory(tableFactory.getType());
            List<MappingBuilder.Mapping> mappingList = tableFactory.getMappingList();
            if (!ObjectUtil.isNull(mappingList)) {
                for (MappingBuilder.Mapping mapping : tableFactory.getMappingList()) {
                    configBuilder.tableMapping(mapping.getType());
                }
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
            List<PropertyBuilder.Property> propertyList = cacheFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                Properties properties = getProperties(propertyList);
                configBuilder.cacheProperties(properties);
            }
        }
        if (typeHandlerFactory != null) {
            configBuilder.typeHandlerFactory(typeHandlerFactory.getType());
            List<TypeHandlerWrapperBuilder.TypeHandlerWrapper> typeHandlerWrapperList = typeHandlerFactory.getTypeHandlerWrapperList();
            if (!ObjectUtil.isNull(typeHandlerWrapperList)) {
                configBuilder.typeHandlerWrapperList(typeHandlerWrapperList.stream()
                        .map(typeHandlerWrapper -> typeHandlerWrapper.getType())
                        .collect(Collectors.toList()));
            }
        }
        if (pluginFactory != null) {
            configBuilder.pluginFactory(pluginFactory.getType());
            List<InterceptorBuilder.Interceptor> interceptorList = pluginFactory.getInterceptorList();
            if (!ObjectUtil.isNull(interceptorList)) {
                configBuilder.interceptor(interceptorList.stream().map(interceptor -> interceptor.getType())
                        .collect(Collectors.toList()));
            }
        }
        if (listenerFactory != null) {
            configBuilder.listenerFactory(listenerFactory.getType());
            List<ListenerBuilder.Listener> listenerList = listenerFactory.getListenerList();
            if (!ObjectUtil.isNull(listenerList)) {
                configBuilder.listener(listenerList.stream().map(listener -> listener.getType())
                        .collect(Collectors.toList()));
            }
        }
        if (transactionFactory != null) {
            configBuilder.transactionFactory(transactionFactory.getType());
            List<PropertyBuilder.Property> propertyList = transactionFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                configBuilder.transactionProperties(getProperties(propertyList));
            }
        }
        if (dataSourceFactory != null) {
            configBuilder.dataSourceFactory(dataSourceFactory.getType());
            List<PropertyBuilder.Property> propertyList = dataSourceFactory.getPropertyList();
            if (!ObjectUtil.isNull(propertyList)) {
                configBuilder.dataSourceProperties(getProperties(propertyList));
            }
        }
        return configBuilder.build();
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
            case XmlConstant.COMPILEFACTORY:
                compileFactory = (CompileFactoryBuilder.CompileFactory) obj;
                break;
            case XmlConstant.INJECTFACTORY:
                injectFactory = (InjectFactoryBuilder.InjectFactory) obj;
                break;
            case XmlConstant.DIALECTFACTORY:
                dialectFactory = (DialectFactoryBuilder.DialectFactory) obj;
                break;
            case XmlConstant.PLUGINFACTORY:
                pluginFactory = (PluginFactoryBuilder.PluginFactory) obj;
                break;
            case XmlConstant.LISTENERFACTORY:
                listenerFactory = (ListenerFactoryBuilder.ListenerFactory) obj;
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
