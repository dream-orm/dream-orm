package com.moxa.dream.system.config;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.mapper.MapperFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.transaction.factory.TransactionFactory;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;

public class Configuration {
    private MapperFactory mapperFactory;
    private TableFactory tableFactory;
    private CacheFactory cacheFactory;
    private TypeHandlerFactory typeHandlerFactory;
    private CompileFactory compileFactory;
    private InjectFactory injectFactory;
    private DialectFactory dialectFactory;
    private InvokerFactory invokerFactory;
    private PluginFactory pluginFactory;
    private ListenerFactory listenerFactory;
    private TransactionFactory transactionFactory;
    private DataSourceFactory dataSourceFactory;
    private StatementHandler statementHandler;
    private ResultSetHandler resultSetHandler;


    public void addMapper(Class type) {
        mapperFactory.addMapper(this, type);
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    public void setMapperFactory(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public void setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
    }

    public CacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public void setCacheFactory(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }

    public TypeHandlerFactory getTypeHandlerFactory() {
        return typeHandlerFactory;
    }

    public void setTypeHandlerFactory(TypeHandlerFactory typeHandlerFactory) {
        this.typeHandlerFactory = typeHandlerFactory;
    }

    public CompileFactory getCompileFactory() {
        return compileFactory;
    }

    public void setCompileFactory(CompileFactory compileFactory) {
        this.compileFactory = compileFactory;
    }

    public InjectFactory getInjectFactory() {
        return injectFactory;
    }

    public void setInjectFactory(InjectFactory injectFactory) {
        this.injectFactory = injectFactory;
    }

    public DialectFactory getDialectFactory() {
        return dialectFactory;
    }

    public void setDialectFactory(DialectFactory dialectFactory) {
        this.dialectFactory = dialectFactory;
    }

    public InvokerFactory getInvokerFactory() {
        return invokerFactory;
    }

    public void setInvokerFactory(InvokerFactory invokerFactory) {
        this.invokerFactory = invokerFactory;
    }

    public PluginFactory getPluginFactory() {
        return pluginFactory;
    }

    public void setPluginFactory(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public ListenerFactory getListenerFactory() {
        return listenerFactory;
    }

    public void setListenerFactory(ListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public StatementHandler getStatementHandler() {
        return statementHandler;
    }

    public void setStatementHandler(StatementHandler statementHandler) {
        this.statementHandler = statementHandler;
    }

    public ResultSetHandler getResultSetHandler() {
        return resultSetHandler;
    }

    public void setResultSetHandler(ResultSetHandler resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
    }
}
