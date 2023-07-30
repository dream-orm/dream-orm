package com.moxa.dream.boot.autoconfigure;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.boot.build.DefaultSessionFactoryBuilder;
import com.moxa.dream.boot.build.SessionFactoryBuilder;
import com.moxa.dream.boot.factory.SpringDataSourceFactory;
import com.moxa.dream.boot.factory.SpringTransactionFactory;
import com.moxa.dream.boot.holder.SpringSessionHolder;
import com.moxa.dream.flex.mapper.DefaultFlexMapper;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.cache.DefaultCacheFactory;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.compile.DefaultCompileFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.core.listener.factory.DefaultListenerFactory;
import com.moxa.dream.system.core.listener.factory.ListenerFactory;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.dialect.DefaultDialectFactory;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.inject.factory.DefaultInjectFactory;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.mapper.DefaultMapperInvokeFactory;
import com.moxa.dream.system.mapper.MapperInvokeFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.plugin.factory.ProxyPluginFactory;
import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.system.transaction.factory.TransactionFactory;
import com.moxa.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.wrapper.TypeHandlerWrapper;
import com.moxa.dream.template.mapper.DefaultTemplateMapper;
import com.moxa.dream.template.mapper.TemplateMapper;
import com.moxa.dream.template.sequence.MySQLSequence;
import com.moxa.dream.template.sequence.Sequence;
import com.moxa.dream.template.session.SessionHolder;
import com.moxa.dream.template.session.SessionTemplate;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
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
     * SQL操作会话工厂创建类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionFactoryBuilder sessionFactoryBuilder() {
        return new DefaultSessionFactoryBuilder();
    }

    /**
     * SQL操作会话获取
     *
     * @param sessionFactory SQL操作会话创建工厂
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionHolder sessionHolder(SessionFactory sessionFactory) {
        return new SpringSessionHolder(sessionFactory);
    }

    /**
     * 目标数据库方言
     *
     * @return
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
            toSQL = new ToMYSQL();
        }
        return toSQL;
    }

    /**
     * 插件工厂
     *
     * @param interceptors 插件处理类
     * @return
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
            interceptors = ObjectUtil.merge(interceptors, interceptorList);
        }
        if (!ObjectUtil.isNull(interceptors)) {
            pluginFactory.interceptors(interceptors);
        }
        return pluginFactory;
    }

    /**
     * 编译工厂
     *
     * @param myFunctionFactory 自定义函数工厂创建类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public CompileFactory compileFactory(@Autowired(required = false) MyFunctionFactory myFunctionFactory) {
        DefaultCompileFactory defaultCompileFactory = new DefaultCompileFactory();
        String strMyFunctionFactory = dreamProperties.getMyFunctionFactory();
        if (!ObjectUtil.isNull(strMyFunctionFactory)) {
            Class<? extends MyFunctionFactory> myFunctionFactoryType = ReflectUtil.loadClass(strMyFunctionFactory);
            myFunctionFactory = ReflectUtil.create(myFunctionFactoryType);
        }
        if (myFunctionFactory != null) {
            defaultCompileFactory.setMyFunctionFactory(myFunctionFactory);
        }
        return defaultCompileFactory;
    }

    /**
     * 注入工厂
     *
     * @param injects 注入类
     * @return
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
            injects = ObjectUtil.merge(injects, injectList);
        }
        if (!ObjectUtil.isNull(injects)) {
            injectFactory.injects(injects);
        }
        return injectFactory;
    }

    /**
     * @param invokers @函数
     * @return
     * @函数工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public InvokerFactory invokerFactory(@Autowired(required = false) Invoker... invokers) {
        InvokerFactory invokerFactory = new DefaultInvokerFactory();
        String[] strInvokers = dreamProperties.getInvokers();
        if (!ObjectUtil.isNull(strInvokers)) {
            Invoker[] invokerList = Arrays.stream(strInvokers).map(invoker -> {
                Class<? extends Invoker> invokerType = ReflectUtil.loadClass(invoker);
                return ReflectUtil.create(invokerType);
            }).collect(Collectors.toList()).toArray(new Invoker[0]);
            invokers = ObjectUtil.merge(invokers, invokerList);
        }
        if (!ObjectUtil.isNull(invokers)) {
            invokerFactory.addInvokers(invokers);
        }
        return invokerFactory;
    }

    /**
     * 编译工厂
     *
     * @param toSQL 目标数据库方言
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DialectFactory dialectFactory(ToSQL toSQL) {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(toSQL);
        return defaultDialectFactory;
    }

    /**
     * 缓存工厂
     *
     * @param cache 缓存器
     * @return
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
     * 类型选择器工厂
     *
     * @param typeHandlerWrappers 类型选择器包装类
     * @return
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
            typeHandlerWrappers = ObjectUtil.merge(typeHandlerWrappers, typeHandlerWrapperList);
        }
        if (!ObjectUtil.isNull(typeHandlerWrappers)) {
            typeHandlerFactory.wrappers(typeHandlerWrappers);
        }
        return typeHandlerFactory;
    }

    /**
     * 监听器工厂
     *
     * @param listeners 监听器
     * @return
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
            listeners = ObjectUtil.merge(listeners, listenerList);
        }
        if (!ObjectUtil.isNull(listeners)) {
            listenerFactory.listeners(listeners);
        }
        return listenerFactory;
    }

    /**
     * 数据源工厂
     *
     * @param dataSource 数据源
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceFactory dataSourceFactory(DataSource dataSource) {
        return new SpringDataSourceFactory(dataSource);
    }

    /**
     * 事务工厂
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TransactionFactory transactionFactory() {
        return new SpringTransactionFactory();
    }

    /**
     * 创建SQL会话创建类
     *
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
     * @return
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
                                         SessionFactoryBuilder sessionFactoryBuilder) {
        configuration.setPluginFactory(pluginFactory);
        configuration.setInvokerFactory(pluginFactory.plugin(invokerFactory));
        configuration.setDialectFactory(pluginFactory.plugin(dialectFactory));
        configuration.setCacheFactory(pluginFactory.plugin(cacheFactory));
        configuration.setTypeHandlerFactory(pluginFactory.plugin(typeHandlerFactory));
        configuration.setCompileFactory(pluginFactory.plugin(compileFactory));
        configuration.setInjectFactory(pluginFactory.plugin(injectFactory));
        configuration.setListenerFactory(pluginFactory.plugin(listenerFactory));
        configuration.setDataSourceFactory(pluginFactory.plugin(dataSourceFactory));
        configuration.setTransactionFactory(pluginFactory.plugin(transactionFactory));
        return sessionFactoryBuilder.build(configuration);
    }

    /**
     * MapperInvoke对象创建工厂
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MapperInvokeFactory mapperInvokeFactory() {
        return new DefaultMapperInvokeFactory();
    }

    /**
     * SQL操作会话
     *
     * @param sessionHolder       SQL操作会话获取
     * @param sessionFactory      SQL操作会话创建工厂
     * @param mapperInvokeFactory MapperInvoke对象创建工厂
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionTemplate sessionTemplate(SessionHolder sessionHolder
            , SessionFactory sessionFactory
            , MapperInvokeFactory mapperInvokeFactory) {
        return new SessionTemplate(sessionHolder, sessionFactory, mapperInvokeFactory);
    }

    /**
     * 主键序列
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Sequence sequence() {
        return new MySQLSequence();
    }


    /**
     * 模板操作接口
     *
     * @param sessionTemplate SQL操作会话
     * @param sequence        主键序列
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TemplateMapper templateMapper(SessionTemplate sessionTemplate, Sequence sequence) {
        return new DefaultTemplateMapper(sessionTemplate, sequence);
    }

    /**
     * 链式操作接口
     *
     * @param sessionTemplate SQL操作会话
     * @param toSQL           目标数据库方言
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FlexMapper flexMapper(SessionTemplate sessionTemplate, ToSQL toSQL) {
        return new DefaultFlexMapper(sessionTemplate, toSQL);
    }
}
