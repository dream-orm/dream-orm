package com.dream.boot.autoconfigure;

import com.dream.antlr.factory.DefaultMyFunctionFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.boot.factory.SpringTransactionFactory;
import com.dream.boot.holder.SpringSessionHolder;
import com.dream.drive.build.DefaultSessionFactoryBuilder;
import com.dream.drive.build.SessionFactoryBuilder;
import com.dream.drive.factory.DriveDataSourceFactory;
import com.dream.flex.mapper.DefaultFlexMapper;
import com.dream.flex.mapper.FlexMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Configuration对象配置类
 */
@EnableConfigurationProperties(DreamProperties.class)
@org.springframework.context.annotation.Configuration
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DreamAutoConfiguration {

    private DreamProperties dreamProperties;

    public DreamAutoConfiguration(DreamProperties dreamProperties) {
        this.dreamProperties = dreamProperties;
    }

    /**
     * @return SQL执行器
     */
    @Bean
    @ConditionalOnMissingBean
    public StatementHandler statementHandler() {
        return new PrepareStatementHandler();
    }

    /**
     * @return 数据映射器
     */
    @Bean
    @ConditionalOnMissingBean
    public ResultSetHandler resultSetHandler() {
        return new DefaultResultSetHandler();
    }

    /**
     * @return SessionFactory建造器
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionFactoryBuilder sessionFactoryBuilder() {
        return new DefaultSessionFactoryBuilder();
    }

    /**
     * @param sessionFactory SQL操作会话创建工厂
     * @return SQL操作会话获取
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionHolder sessionHolder(SessionFactory sessionFactory) {
        return new SpringSessionHolder(sessionFactory);
    }

    /**
     * @return 目标数据库方言
     */
    @Bean
    @ConditionalOnMissingBean
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

    /**
     * @param interceptors 插件处理类
     * @return 插件工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public PluginFactory pluginFactory(@Autowired(required = false) Interceptor... interceptors) {
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
    @ConditionalOnMissingBean
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
    @ConditionalOnMissingBean
    public CompileFactory compileFactory(MyFunctionFactory myFunctionFactory) {
        DefaultCompileFactory defaultCompileFactory = new DefaultCompileFactory();
        defaultCompileFactory.setMyFunctionFactory(myFunctionFactory);
        return defaultCompileFactory;
    }

    /**
     * @param injects 注入类
     * @return 注入工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public InjectFactory injectFactory(@Autowired(required = false) Inject... injects) {
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
    @ConditionalOnMissingBean
    public InvokerFactory invokerFactory() {
        return new DefaultInvokerFactory();
    }

    /**
     * @param toSQL 目标数据库方言
     * @return 编译工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public DialectFactory dialectFactory(ToSQL toSQL) {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(toSQL);
        return defaultDialectFactory;
    }

    /**
     * @param cache 缓存器
     * @return 缓存工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheFactory cacheFactory(@Autowired(required = false) Cache cache) {
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

    /**
     * @param typeHandlerWrappers 类型选择器包装类
     * @return 类型选择器工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public TypeHandlerFactory typeHandlerFactory(@Autowired(required = false) TypeHandlerWrapper... typeHandlerWrappers) {
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

    /**
     * @param listeners 监听器
     * @return 监听器工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public ListenerFactory listenerFactory(@Autowired(required = false) Listener... listeners) {
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
    @ConditionalOnMissingBean
    public DataSourceFactory dataSourceFactory(DataSource dataSource) {
        return new DriveDataSourceFactory(dataSource);
    }

    /**
     * @return 事务工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public TransactionFactory transactionFactory() {
        return new SpringTransactionFactory();
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
     * @param statementHandler      SQL执行器
     * @param resultSetHandler      数据映射器
     * @param sessionFactoryBuilder SQL操作会话工厂创建类
     * @return 创建SQL会话创建类
     */
    @Bean
    @ConditionalOnMissingBean
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
    @ConditionalOnMissingBean
    public SessionTemplate sessionTemplate(SessionHolder sessionHolder
            , SessionFactory sessionFactory) {
        return new SessionTemplate(sessionHolder, sessionFactory);
    }

    /**
     * @return 主键序列
     */
    @Bean
    @ConditionalOnMissingBean
    public Sequence sequence() {
        return new AutoIncrementSequence();
    }


    /**
     * @param sessionTemplate SQL操作会话
     * @param sequence        主键序列
     * @return 模板操作接口
     */
    @Bean
    @ConditionalOnMissingBean
    public TemplateMapper templateMapper(SessionTemplate sessionTemplate, Sequence sequence) {
        return new DefaultTemplateMapper(sessionTemplate, sequence);
    }

    /**
     * @param sessionTemplate SQL操作会话
     * @return Flex操作接口
     */
    @Bean
    @ConditionalOnMissingBean
    public FlexMapper flexMapper(SessionTemplate sessionTemplate) {
        return new DefaultFlexMapper(sessionTemplate);
    }
}
