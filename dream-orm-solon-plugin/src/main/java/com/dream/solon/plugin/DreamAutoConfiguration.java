package com.dream.solon.plugin;

import com.dream.antlr.factory.DefaultMyFunctionFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.drive.build.DefaultSessionFactoryBuilder;
import com.dream.drive.build.SessionFactoryBuilder;
import com.dream.drive.config.DreamProperties;
import com.dream.drive.factory.DriveDataSourceFactory;
import com.dream.flex.mapper.DefaultFlexMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.jdbc.mapper.DefaultJdbcMapper;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.solon.factory.SolonTransactionFactory;
import com.dream.solon.holder.SolonSessionHolder;
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
import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.listener.factory.DefaultListenerFactory;
import com.dream.system.core.listener.factory.ListenerFactory;
import com.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.session.SessionFactory;
import com.dream.system.core.statementhandler.PrepareStatementHandler;
import com.dream.system.core.statementhandler.StatementHandler;
import com.dream.system.datasource.DataSourceFactory;
import com.dream.system.dialect.DefaultDialectFactory;
import com.dream.system.dialect.DialectFactory;
import com.dream.system.inject.Inject;
import com.dream.system.inject.factory.DefaultInjectFactory;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.system.plugin.factory.PluginFactory;
import com.dream.system.plugin.factory.ProxyPluginFactory;
import com.dream.system.plugin.interceptor.Interceptor;
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
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Configuration对象配置类
 */
@org.noear.solon.annotation.Configuration
@Condition(onClass = DataSource.class)
public class DreamAutoConfiguration {

    @org.noear.solon.annotation.Inject("${dream}")
    private DreamProperties dreamProperties;

    /**
     * @return SQL执行器
     */
    @Bean
    @Condition(onMissingBean = StatementHandler.class)
    public StatementHandler statementHandler() {
        return new PrepareStatementHandler();
    }

    /**
     * @return 数据映射器
     */
    @Bean
    @Condition(onMissingBean = ResultSetHandler.class)
    public ResultSetHandler resultSetHandler() {
        return new DefaultResultSetHandler();
    }

    /**
     * @return SQL操作会话工厂创建类
     */
    @Bean
    @Condition(onMissingBean = SessionFactoryBuilder.class)
    public SessionFactoryBuilder sessionFactoryBuilder() {
        return new DefaultSessionFactoryBuilder();
    }

    /**
     * @param sessionFactory SQL操作会话创建工厂
     * @return SQL操作会话获取
     */
    @Bean
    @Condition(onMissingBean = SessionHolder.class)
    public SessionHolder sessionHolder(SessionFactory sessionFactory) {
        return new SolonSessionHolder(sessionFactory);
    }

    /**
     * @return 目标数据库方言
     */
    @Bean
    @Condition(onMissingBean = ToSQL.class)
    public ToSQL toSQL() {
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

    @Bean
    @Condition(onMissingBean = Interceptor[].class)
    public Interceptor[] interceptors() {
        return new Interceptor[0];
    }

    /**
     * @param interceptors 插件处理类
     * @return 插件工厂
     */
    @Bean
    @Condition(onMissingBean = PluginFactory.class)
    public PluginFactory pluginFactory(Interceptor... interceptors) {
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
     * @return 自定义函数
     */
    @Bean
    @Condition(onMissingBean = MyFunctionFactory.class)
    public MyFunctionFactory myFunctionFactory() {
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
     * @param myFunctionFactory 自定义函数工厂创建类
     * @return 编译工厂
     */
    @Bean
    @Condition(onMissingBean = CompileFactory.class)
    public CompileFactory compileFactory(MyFunctionFactory myFunctionFactory) {
        DefaultCompileFactory defaultCompileFactory = new DefaultCompileFactory();
        defaultCompileFactory.setMyFunctionFactory(myFunctionFactory);
        return defaultCompileFactory;
    }

    @Bean
    @Condition(onMissingBean = Inject[].class)
    public Inject[] injects() {
        return new Inject[0];
    }

    /**
     * @param injects 注入类
     * @return 注入工厂
     */
    @Bean
    @Condition(onMissingBean = InjectFactory.class)
    public InjectFactory injectFactory(Inject... injects) {
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
            injectFactory.addInjects(injects);
        }
        return injectFactory;
    }

    /**
     * @return @函数工厂
     */
    @Bean
    @Condition(onMissingBean = InvokerFactory.class)
    public InvokerFactory invokerFactory() {
        return new DefaultInvokerFactory();
    }

    /**
     * @param toSQL 目标数据库方言
     * @return 编译工厂
     */
    @Bean
    @Condition(onMissingBean = DialectFactory.class)
    public DialectFactory dialectFactory(ToSQL toSQL) {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(toSQL);
        return defaultDialectFactory;
    }

    @Bean
    @Condition(onMissingBean = Cache.class)
    public Cache cache() {
        return new NoneCache();
    }

    /**
     * @return 缓存工厂
     */
    @Bean
    @Condition(onMissingBean = CacheFactory.class)
    public CacheFactory cacheFactory(Cache cache) {
        if (cache != null && cache instanceof NoneCache) {
            cache = null;
        }
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


    @Bean
    @Condition(onMissingBean = TypeHandlerWrapper[].class)
    public TypeHandlerWrapper[] typeHandlerWrappers() {
        return new TypeHandlerWrapper[0];
    }

    /**
     * @param typeHandlerWrappers 类型选择器包装类
     * @return 类型选择器工厂
     */
    @Bean
    @Condition(onMissingBean = TypeHandlerFactory.class)
    public TypeHandlerFactory typeHandlerFactory(TypeHandlerWrapper... typeHandlerWrappers) {
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

    @Bean
    @Condition(onMissingBean = Listener[].class)
    public Listener[] listeners() {
        return new Listener[0];
    }

    /**
     * @param listeners 监听器
     * @return 监听器工厂
     */
    @Bean
    @Condition(onMissingBean = ListenerFactory.class)
    public ListenerFactory listenerFactory(Listener... listeners) {
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
     * @param dataSource 数据源
     * @return 数据源工厂
     */
    @Bean
    @Condition(onMissingBean = DataSourceFactory.class)
    public DataSourceFactory dataSourceFactory(DataSource dataSource) {
        return new DriveDataSourceFactory(dataSource);
    }

    /**
     * @return 事务工厂
     */
    @Bean
    @Condition(onMissingBean = TransactionFactory.class)
    public TransactionFactory transactionFactory() {
        return new SolonTransactionFactory();
    }

    /**
     * @param configuration         配置信息
     * @param pluginFactory         插件工厂
     * @param invokerFactory        @函数工厂
     * @param dialectFactory        编译工厂
     * @param cacheFactory          缓存工厂
     * @param typeHandlerFactory    类型选择器工厂
     * @param compileFactory        编译工厂
     * @param injectFactory         注入工厂
     * @param listenerFactory       监听工厂
     * @param dataSourceFactory     数据源工厂
     * @param transactionFactory    事务工厂
     * @param sessionFactoryBuilder SQL操作会话工厂创建类
     * @return 创建SQL会话创建类
     */
    @Bean
    @Condition(onMissingBean = SessionFactory.class)
    public SessionFactory sessionFactory(Configuration configuration,
                                         PluginFactory pluginFactory,
                                         InvokerFactory invokerFactory,
                                         DialectFactory dialectFactory,
                                         CacheFactory cacheFactory,
                                         TypeHandlerFactory typeHandlerFactory,
                                         CompileFactory compileFactory,
                                         InjectFactory injectFactory,
                                         ListenerFactory listenerFactory,
                                         DataSourceFactory dataSourceFactory,
                                         TransactionFactory transactionFactory,
                                         StatementHandler statementHandler,
                                         ResultSetHandler resultSetHandler,
                                         SessionFactoryBuilder sessionFactoryBuilder) {
        configuration.setPluginFactory(pluginFactory);
        configuration.setInvokerFactory(invokerFactory);
        configuration.setDialectFactory(dialectFactory);
        configuration.setCacheFactory(cacheFactory);
        configuration.setTypeHandlerFactory(typeHandlerFactory);
        configuration.setCompileFactory(compileFactory);
        configuration.setInjectFactory(injectFactory);
        configuration.setListenerFactory(listenerFactory);
        configuration.setDataSourceFactory(dataSourceFactory);
        configuration.setTransactionFactory(transactionFactory);
        configuration.setStatementHandler(statementHandler);
        configuration.setResultSetHandler(resultSetHandler);
        return sessionFactoryBuilder.build(configuration);
    }

    /**
     * @param sessionHolder  SQL操作会话获取
     * @param sessionFactory SQL操作会话创建工厂
     * @return SQL操作会话
     */
    @Bean
    @Condition(onMissingBean = SessionTemplate.class)
    public SessionTemplate sessionTemplate(SessionHolder sessionHolder
            , SessionFactory sessionFactory) {
        return new SessionTemplate(sessionHolder, sessionFactory);
    }

    /**
     * @return 主键序列
     */
    @Bean
    @Condition(onMissingBean = Sequence.class)
    public Sequence sequence() {
        return new AutoIncrementSequence();
    }

    /**
     * @param sessionTemplate SQL操作会话
     * @param sequence        主键序列
     * @return 模板操作接口
     */
    @Bean
    @Condition(onMissingBean = TemplateMapper.class)
    public TemplateMapper templateMapper(SessionTemplate sessionTemplate, Sequence sequence) {
        return new DefaultTemplateMapper(sessionTemplate, sequence);
    }

    /**
     * @return struct工厂
     */
    @Bean
    @Condition(onMissingBean = FlexMapper.class)
    public StructFactory structFactory(ToSQL toSQL) {
        return new DefaultStructFactory(toSQL);
    }

    /**
     * @param sessionTemplate SQL操作会话
     * @param structFactory   struct工厂
     * @return Flex操作接口
     */
    @Bean
    @Condition(onMissingBean = FlexMapper.class)
    public FlexMapper flexMapper(SessionTemplate sessionTemplate, StructFactory structFactory) {
        return new DefaultFlexMapper(sessionTemplate, structFactory);
    }

    /**
     * @param sessionTemplate SQL操作会话
     * @param structFactory   struct工厂
     * @return Stream操作接口
     */
    @Bean
    @Condition(onMissingBean = StreamMapper.class)
    public StreamMapper streamMapper(SessionTemplate sessionTemplate, StructFactory structFactory) {
        return new DefaultStreamMapper(sessionTemplate, structFactory);
    }

    /**
     * @param sessionTemplate SQL操作会话
     * @return 不翻译操作接口
     */
    @Bean
    @Condition(onMissingBean = JdbcMapper.class)
    public JdbcMapper jdbcMapper(SessionTemplate sessionTemplate, ToSQL toSQL) {
        return new DefaultJdbcMapper(sessionTemplate, toSQL);
    }

    final class NoneCache implements Cache {

        @Override
        public void put(MappedStatement mappedStatement, Object value) {
            throw new UnsupportedOperationException("不支持的缓存操作");
        }

        @Override
        public Object get(MappedStatement mappedStatement) {
            throw new UnsupportedOperationException("不支持的缓存操作");
        }

        @Override
        public void remove(MappedStatement mappedStatement) {
            throw new UnsupportedOperationException("不支持的缓存操作");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("不支持的缓存操作");
        }
    }
}
