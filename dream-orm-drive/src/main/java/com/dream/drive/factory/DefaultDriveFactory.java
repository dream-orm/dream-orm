package com.dream.drive.factory;

import com.dream.antlr.factory.DefaultMyFunctionFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.drive.build.DefaultSessionFactoryBuilder;
import com.dream.drive.build.SessionFactoryBuilder;
import com.dream.drive.config.DefaultConfig;
import com.dream.drive.config.DreamProperties;
import com.dream.drive.holder.DriveSessionHolder;
import com.dream.flex.mapper.DefaultFlexMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.jdbc.mapper.DefaultJdbcMapper;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.stream.mapper.DefaultStreamMapper;
import com.dream.stream.mapper.StreamMapper;
import com.dream.struct.factory.DefaultStructFactory;
import com.dream.struct.factory.StructFactory;
import com.dream.system.antlr.factory.DefaultInvokerFactory;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.compile.CompileFactory;
import com.dream.system.compile.DefaultCompileFactory;
import com.dream.system.config.Configuration;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.listener.factory.DefaultListenerFactory;
import com.dream.system.core.listener.factory.ListenerFactory;
import com.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.system.core.session.SessionFactory;
import com.dream.system.core.statementhandler.PrepareStatementHandler;
import com.dream.system.core.statementhandler.StatementHandler;
import com.dream.system.datasource.DataSourceFactory;
import com.dream.system.dialect.DefaultDialectFactory;
import com.dream.system.dialect.DialectFactory;
import com.dream.system.inject.Inject;
import com.dream.system.inject.factory.DefaultInjectFactory;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.system.mapper.DefaultMapperFactory;
import com.dream.system.plugin.factory.PluginFactory;
import com.dream.system.plugin.factory.ProxyPluginFactory;
import com.dream.system.plugin.interceptor.Interceptor;
import com.dream.system.table.factory.DefaultTableFactory;
import com.dream.system.transaction.factory.TransactionFactory;
import com.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.wrapper.TypeHandlerWrapper;
import com.dream.template.mapper.DefaultTemplateMapper;
import com.dream.template.mapper.TemplateMapper;
import com.dream.template.sequence.AutoIncrementSequence;
import com.dream.template.sequence.Sequence;
import com.dream.template.session.SessionHolder;
import com.dream.template.session.SessionTemplate;
import com.dream.util.common.ObjectUtil;
import com.dream.util.reflect.ReflectUtil;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultDriveFactory implements DriveFactory {
    protected SessionFactory sessionFactory;
    protected SessionTemplate sessionTemplate;
    protected TemplateMapper templateMapper;
    protected FlexMapper flexMapper;
    protected StreamMapper streamMapper;
    protected JdbcMapper jdbcMapper;
    protected ToSQL toSQL;
    protected DreamProperties dreamProperties;

    public DefaultDriveFactory(DataSource dataSource) {
        this(dataSource, null, null);
    }

    public DefaultDriveFactory(DataSource dataSource, List<String> tablePackages, List<String> mapperPackages) {
        this(dataSource, tablePackages, mapperPackages, new DreamProperties());
    }

    public DefaultDriveFactory(DataSource dataSource, List<String> tablePackages, List<String> mapperPackages, DreamProperties dreamProperties) {
        this.dreamProperties = dreamProperties;
        this.toSQL = toSQL();
        this.sessionFactory = sessionFactory(dataSource, tablePackages, mapperPackages);
        this.sessionTemplate = sessionTemplate(sessionHolder(), this.sessionFactory);
        this.templateMapper = templateMapper(sessionTemplate, sequence());
        StructFactory structFactory = structFactory(toSQL);
        this.flexMapper = flexMapper(sessionTemplate, structFactory);
        this.streamMapper = streamMapper(sessionTemplate, structFactory);
        this.jdbcMapper = jdbcMapper(this.sessionTemplate, toSQL);
    }

    @Override
    public Session session() {
        return sessionTemplate;
    }

    @Override
    public TemplateMapper templateMapper() {
        return templateMapper;
    }

    @Override
    public FlexMapper flexMapper() {
        return flexMapper;
    }

    @Override
    public StreamMapper streamMapper() {
        return streamMapper;
    }

    @Override
    public JdbcMapper jdbcMapper() {
        return jdbcMapper;
    }

    protected TemplateMapper templateMapper(SessionTemplate sessionTemplate, Sequence sequence) {
        return new DefaultTemplateMapper(sessionTemplate, sequence);
    }

    protected FlexMapper flexMapper(SessionTemplate sessionTemplate, StructFactory structFactory) {
        return new DefaultFlexMapper(sessionTemplate, structFactory);
    }

    protected StreamMapper streamMapper(SessionTemplate sessionTemplate, StructFactory structFactory) {
        return new DefaultStreamMapper(sessionTemplate, structFactory);
    }

    protected JdbcMapper jdbcMapper(Session session, ToSQL toSQL) {
        return new DefaultJdbcMapper(session, toSQL);
    }

    /**
     * 创建SQL执行器
     *
     * @return SQL执行器
     */
    protected StatementHandler statementHandler() {
        return new PrepareStatementHandler();
    }

    /**
     * 创建数据映射器
     *
     * @return 数据映射器
     */
    protected ResultSetHandler resultSetHandler() {
        return new DefaultResultSetHandler();
    }

    /**
     * SQL操作会话工厂创建类
     *
     * @return
     */
    protected SessionFactoryBuilder sessionFactoryBuilder() {
        return new DefaultSessionFactoryBuilder();
    }

    /**
     * SQL操作会话获取
     *
     * @return
     */
    protected SessionHolder sessionHolder() {
        return new DriveSessionHolder(sessionFactory);
    }

    /**
     * 目标数据库方言
     *
     * @return
     */
    protected ToSQL toSQL() {
        ToSQL toSQL;
        String strToSQL = dreamProperties.getToSQL();
        if (!ObjectUtil.isNull(strToSQL)) {
            Class<? extends ToSQL> toSQLType = ReflectUtil.loadClass(strToSQL);
            toSQL = ReflectUtil.create(toSQLType);
        } else {
            toSQL = new ToMySQL();
        }
        return toSQL;
    }

    protected Interceptor[] interceptors() {
        return new Interceptor[0];
    }

    /**
     * struct工厂
     *
     * @param toSQL
     * @return
     */
    protected StructFactory structFactory(ToSQL toSQL) {
        return new DefaultStructFactory(toSQL);
    }

    /**
     * 插件工厂
     *
     * @return
     */
    protected PluginFactory pluginFactory() {
        Interceptor[] interceptors = interceptors();
        PluginFactory pluginFactory = new ProxyPluginFactory();
        String[] strInterceptors = dreamProperties.getInterceptors();
        if (!ObjectUtil.isNull(strInterceptors)) {
            Interceptor[] interceptorList = Arrays.stream(strInterceptors).map(interceptor -> {
                Class<? extends Interceptor> interceptorType = ReflectUtil.loadClass(interceptor);
                return ReflectUtil.create(interceptorType);
            }).collect(Collectors.toList()).toArray(new Interceptor[0]);
            interceptors = ObjectUtil.merge(interceptors, interceptorList).toArray(new Interceptor[0]);
        }
        if (!ObjectUtil.isNull(interceptors)) {
            pluginFactory.interceptors(interceptors);
        }
        return pluginFactory;
    }

    /**
     * 自定义函数
     *
     * @return 自定义函数
     */
    protected MyFunctionFactory myFunctionFactory() {
        String strMyFunctionFactory = dreamProperties.getMyFunctionFactory();
        MyFunctionFactory myFunctionFactory;
        if (!ObjectUtil.isNull(strMyFunctionFactory)) {
            Class<? extends MyFunctionFactory> myFunctionFactoryType = ReflectUtil.loadClass(strMyFunctionFactory);
            myFunctionFactory = ReflectUtil.create(myFunctionFactoryType);
        } else {
            myFunctionFactory = new DefaultMyFunctionFactory();
        }
        return myFunctionFactory;
    }

    /**
     * 编译工厂
     *
     * @return
     */
    protected CompileFactory compileFactory() {
        DefaultCompileFactory defaultCompileFactory = new DefaultCompileFactory();
        defaultCompileFactory.setMyFunctionFactory(myFunctionFactory());
        return defaultCompileFactory;
    }

    protected Inject[] injects() {
        return new Inject[0];
    }

    /**
     * 注入工厂
     *
     * @return
     */
    protected InjectFactory injectFactory() {
        Inject[] injects = injects();
        InjectFactory injectFactory = new DefaultInjectFactory();
        String[] strInjects = dreamProperties.getInjects();
        if (!ObjectUtil.isNull(strInjects)) {
            Inject[] injectList = Arrays.stream(strInjects).map(inject -> {
                Class<? extends Inject> injectType = ReflectUtil.loadClass(inject);
                return ReflectUtil.create(injectType);
            }).collect(Collectors.toList()).toArray(new Inject[0]);
            injects = ObjectUtil.merge(injects, injectList).toArray(new Inject[0]);
        }
        if (!ObjectUtil.isNull(injects)) {
            injectFactory.injects(injects);
        }
        return injectFactory;
    }

    protected Invoker[] invokers() {
        return new Invoker[0];
    }

    /**
     * @return
     * @函数工厂
     */
    protected InvokerFactory invokerFactory() {
        Invoker[] invokers = invokers();
        InvokerFactory invokerFactory = new DefaultInvokerFactory();
        String[] strInvokers = dreamProperties.getInvokers();
        if (!ObjectUtil.isNull(strInvokers)) {
            Invoker[] invokerList = Arrays.stream(strInvokers).map(invoker -> {
                Class<? extends Invoker> invokerType = ReflectUtil.loadClass(invoker);
                return ReflectUtil.create(invokerType);
            }).collect(Collectors.toList()).toArray(new Invoker[0]);
            invokers = ObjectUtil.merge(invokers, invokerList).toArray(new Invoker[0]);
        }
        if (!ObjectUtil.isNull(invokers)) {
            invokerFactory.addInvokers(invokers);
        }
        return invokerFactory;
    }

    /**
     * 编译工厂
     *
     * @return
     */
    protected DialectFactory dialectFactory() {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(toSQL);
        return defaultDialectFactory;
    }

    protected Cache cache() {
        return null;
    }

    /**
     * 缓存工厂
     *
     * @return
     */
    protected CacheFactory cacheFactory() {
        Cache cache = cache();
        DefaultCacheFactory defaultCacheFactory = new DefaultCacheFactory();
        String strCache = dreamProperties.getCache();
        if (!ObjectUtil.isNull(strCache)) {
            Class<? extends Cache> cacheType = ReflectUtil.loadClass(strCache);
            cache = ReflectUtil.create(cacheType);
        }
        if (cache != null) {
            defaultCacheFactory.setCache(cache);
        }
        return defaultCacheFactory;
    }

    protected TypeHandlerWrapper[] typeHandlerWrappers() {
        return new TypeHandlerWrapper[0];
    }

    /**
     * 类型选择器工厂
     *
     * @return
     */
    protected TypeHandlerFactory typeHandlerFactory() {
        TypeHandlerWrapper[] typeHandlerWrappers = typeHandlerWrappers();
        TypeHandlerFactory typeHandlerFactory = new DefaultTypeHandlerFactory();
        String[] strTypeHandlerWrappers = dreamProperties.getTypeHandlerWrappers();
        if (!ObjectUtil.isNull(strTypeHandlerWrappers)) {
            TypeHandlerWrapper[] typeHandlerWrapperList = Arrays.stream(strTypeHandlerWrappers).map(typeHandlerWrapper -> {
                Class<? extends TypeHandlerWrapper> typeHandlerWrapperType = ReflectUtil.loadClass(typeHandlerWrapper);
                return ReflectUtil.create(typeHandlerWrapperType);
            }).collect(Collectors.toList()).toArray(new TypeHandlerWrapper[0]);
            typeHandlerWrappers = ObjectUtil.merge(typeHandlerWrappers, typeHandlerWrapperList).toArray(new TypeHandlerWrapper[0]);
        }
        if (!ObjectUtil.isNull(typeHandlerWrappers)) {
            typeHandlerFactory.wrappers(typeHandlerWrappers);
        }
        return typeHandlerFactory;
    }

    protected Listener[] listeners() {
        return new Listener[0];
    }

    /**
     * 监听器工厂
     *
     * @return
     */
    protected ListenerFactory listenerFactory() {
        Listener[] listeners = listeners();
        ListenerFactory listenerFactory = new DefaultListenerFactory();
        String[] strListeners = dreamProperties.getListeners();
        if (!ObjectUtil.isNull(strListeners)) {
            Listener[] listenerList = Arrays.stream(strListeners).map(listener -> {
                Class<? extends Listener> listenerType = ReflectUtil.loadClass(listener);
                return ReflectUtil.create(listenerType);
            }).collect(Collectors.toList()).toArray(new Listener[0]);
            listeners = ObjectUtil.merge(listeners, listenerList).toArray(new Listener[0]);
        }
        if (!ObjectUtil.isNull(listeners)) {
            listenerFactory.listeners(listeners);
        }
        return listenerFactory;
    }

    /**
     * 数据源工厂
     *
     * @return
     */
    protected DataSourceFactory dataSourceFactory(DataSource dataSource) {
        return new DriveDataSourceFactory(dataSource);
    }

    /**
     * 事务工厂
     *
     * @return
     */
    public TransactionFactory transactionFactory() {
        return new DriveTransactionFactory();
    }


    /**
     * 创建SQL会话创建类
     *
     * @return
     */
    protected SessionFactory sessionFactory(DataSource dataSource, List<String> tablePackages, List<String> mapperPackages) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.setTableFactory(new DefaultTableFactory());
        defaultConfig.setMapperFactory(new DefaultMapperFactory());
        defaultConfig.setTablePackages(tablePackages);
        defaultConfig.setMapperPackages(mapperPackages);
        Configuration configuration = defaultConfig.toConfiguration();
        configuration.setPluginFactory(pluginFactory());
        configuration.setInvokerFactory(invokerFactory());
        configuration.setDialectFactory(dialectFactory());
        configuration.setCacheFactory(cacheFactory());
        configuration.setTypeHandlerFactory(typeHandlerFactory());
        configuration.setCompileFactory(compileFactory());
        configuration.setInjectFactory(injectFactory());
        configuration.setListenerFactory(listenerFactory());
        configuration.setDataSourceFactory(dataSourceFactory(dataSource));
        configuration.setTransactionFactory(transactionFactory());
        configuration.setStatementHandler(statementHandler());
        configuration.setResultSetHandler(resultSetHandler());
        return sessionFactoryBuilder().build(configuration);
    }

    /**
     * SQL操作会话
     *
     * @param sessionHolder  SQL操作会话获取
     * @param sessionFactory SQL操作会话创建工厂
     * @return
     */
    protected SessionTemplate sessionTemplate(SessionHolder sessionHolder
            , SessionFactory sessionFactory) {
        return new SessionTemplate(sessionHolder, sessionFactory);
    }

    /**
     * 主键序列
     *
     * @return
     */
    protected Sequence sequence() {
        return new AutoIncrementSequence();
    }

}
