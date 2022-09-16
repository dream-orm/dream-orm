package com.moxa.dream.drive.config;

import com.moxa.dream.drive.alias.AliasFactory;
import com.moxa.dream.system.cache.factory.CacheFactory;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.mapper.factory.MapperFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.transaction.factory.TransactionFactory;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;

import java.util.List;


public class DefaultConfig {
    private AliasFactory aliasFactory;
    private TableFactory tableFactory;
    private MapperFactory mapperFactory;
    private CacheFactory cacheFactory;
    private TypeHandlerFactory typeHandlerFactory;
    private CompileFactory compileFactory;
    private InjectFactory injectFactory;
    private DialectFactory dialectFactory;
    private TransactionFactory transactionFactory;
    private DataSourceFactory dataSourceFactory;
    private PluginFactory pluginFactory;
    private ListenerFactory listenerFactory;
    private List<String> mapperPackages;
    private List<String> tablePackages;

    public AliasFactory getAliasFactory() {
        return aliasFactory;
    }

    public DefaultConfig setAliasFactory(AliasFactory aliasFactory) {
        this.aliasFactory = aliasFactory;
        return this;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public DefaultConfig setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
        return this;
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    public DefaultConfig setMapperFactory(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
        return this;
    }

    public CacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public DefaultConfig setCacheFactory(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
        return this;
    }


    public TypeHandlerFactory getTypeHandlerFactory() {
        return typeHandlerFactory;
    }

    public DefaultConfig setTypeHandlerFactory(TypeHandlerFactory typeHandlerFactory) {
        this.typeHandlerFactory = typeHandlerFactory;
        return this;
    }

    public CompileFactory getCompileFactory() {
        return compileFactory;
    }

    public DefaultConfig setCompileFactory(CompileFactory compileFactory) {
        this.compileFactory = compileFactory;
        return this;
    }

    public InjectFactory getInjectFactory() {
        return injectFactory;
    }

    public DefaultConfig setInjectFactory(InjectFactory injectFactory) {
        this.injectFactory = injectFactory;
        return this;
    }

    public DialectFactory getDialectFactory() {
        return dialectFactory;
    }

    public DefaultConfig setDialectFactory(DialectFactory dialectFactory) {
        this.dialectFactory = dialectFactory;
        return this;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public DefaultConfig setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
        return this;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public DefaultConfig setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
        return this;
    }

    public PluginFactory getPluginFactory() {
        return pluginFactory;
    }

    public DefaultConfig setPluginFactory(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
        return this;
    }

    public ListenerFactory getListenerFactory() {
        return listenerFactory;
    }

    public DefaultConfig setListenerFactory(ListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
        return this;
    }

    public List<String> getMapperPackages() {
        return mapperPackages;
    }

    public DefaultConfig setMapperPackages(List<String> mapperPackages) {
        this.mapperPackages = mapperPackages;
        return this;
    }

    public List<String> getTablePackages() {
        return tablePackages;
    }

    public DefaultConfig setTablePackages(List<String> tablePackages) {
        this.tablePackages = tablePackages;
        return this;
    }
}
