package com.moxa.dream.solon.plugin;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.mapper.DefaultFlexMapper;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.solon.build.DefaultSessionFactoryBuilder;
import com.moxa.dream.solon.build.SessionFactoryBuilder;
import com.moxa.dream.solon.factory.SolonDataSourceFactory;
import com.moxa.dream.solon.factory.SolonTransactionFactory;
import com.moxa.dream.solon.holder.SolonSessionHolder;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.cache.DefaultCacheFactory;
import com.moxa.dream.system.cache.MemoryCache;
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
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.stream.Collectors;

@org.noear.solon.annotation.Configuration
@Condition(onClass = DataSource.class)
public class DreamAutoConfiguration {

    @org.noear.solon.annotation.Inject("${dream}")
    private DreamProperties dreamProperties;

    @Bean
    @Condition(onMissingBean = SessionFactoryBuilder.class)
    public SessionFactoryBuilder sessionFactoryBuilder() {
        return new DefaultSessionFactoryBuilder();
    }

    @Bean
    @Condition(onMissingBean = SessionHolder.class)
    public SessionHolder sessionHolder(SessionFactory sessionFactory) {
        return new SolonSessionHolder(sessionFactory);
    }

    @Bean
    @Condition(onMissingBean = ToSQL.class)
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

    @Bean
    @Condition(onMissingBean = Cache.class)
    public Cache cache() {
        return new MemoryCache(100, 0.25);
    }

    @Bean
    @Condition(onMissingBean = Interceptor[].class)
    public Interceptor[] interceptors() {
        return new Interceptor[0];
    }

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
            interceptors = ObjectUtil.merge(interceptors, interceptorList);
        }
        if (!ObjectUtil.isNull(interceptors)) {
            pluginFactory.interceptors(interceptors);
        }
        return pluginFactory;
    }

    @Bean
    @Condition(onMissingBean = MyFunctionFactory.class)
    public MyFunctionFactory myFunctionFactory() {
        return function -> null;
    }

    @Bean
    @Condition(onMissingBean = CompileFactory.class)
    public CompileFactory compileFactory(MyFunctionFactory myFunctionFactory) {
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

    @Bean
    @Condition(onMissingBean = Inject[].class)
    public Inject[] injects() {
        return new Inject[0];
    }

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
            injects = ObjectUtil.merge(injects, injectList);
        }
        if (!ObjectUtil.isNull(injects)) {
            injectFactory.injects(injects);
        }
        return injectFactory;
    }

    @Bean
    @Condition(onMissingBean = Invoker[].class)
    public Invoker[] invokers() {
        return new Invoker[0];
    }

    @Bean
    @Condition(onMissingBean = InvokerFactory.class)
    public InvokerFactory invokerFactory(Invoker... invokers) {
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

    @Bean
    @Condition(onMissingBean = DialectFactory.class)
    public DialectFactory dialectFactory(ToSQL toSQL) {
        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
        defaultDialectFactory.setToSQL(toSQL);
        return defaultDialectFactory;
    }

    @Bean
    @Condition(onMissingBean = CacheFactory.class)
    public CacheFactory cacheFactory(Cache cache) {
        DefaultCacheFactory defaultCacheFactory = new DefaultCacheFactory();
        String strCache = dreamProperties.getCache();
        if (!ObjectUtil.isNull(strCache)) {
            Class<? extends Cache> cacheType = ReflectUtil.loadClass(strCache);
            cache = ReflectUtil.create(cacheType);
        }
        defaultCacheFactory.setCache(cache);
        return defaultCacheFactory;
    }

    @Bean
    @Condition(onMissingBean = TypeHandlerWrapper[].class)
    public TypeHandlerWrapper[] typeHandlerWrappers() {
        return new TypeHandlerWrapper[0];
    }

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
            typeHandlerWrappers = ObjectUtil.merge(typeHandlerWrappers, typeHandlerWrapperList);
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
            listeners = ObjectUtil.merge(listeners, listenerList);
        }
        if (!ObjectUtil.isNull(listeners)) {
            listenerFactory.listeners(listeners);
        }
        return listenerFactory;
    }

    @Bean
    @Condition(onMissingBean = DataSourceFactory.class)
    public DataSourceFactory dataSourceFactory(DataSource dataSource) {
        return new SolonDataSourceFactory(dataSource);
    }

    @Bean
    @Condition(onMissingBean = TransactionFactory.class)
    public TransactionFactory transactionFactory() {
        return new SolonTransactionFactory();
    }

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

    @Bean
    @Condition(onMissingBean = MapperInvokeFactory.class)
    public MapperInvokeFactory mapperInvokeFactory() {
        return new DefaultMapperInvokeFactory();
    }

    @Bean
    @Condition(onMissingBean = SessionTemplate.class)
    public SessionTemplate sessionTemplate(SessionHolder sessionHolder
            , SessionFactory sessionFactory
            , MapperInvokeFactory mapperInvokeFactory) {
        return new SessionTemplate(sessionHolder, sessionFactory, mapperInvokeFactory);
    }

    @Bean
    @Condition(onMissingBean = Sequence.class)
    public Sequence sequence() {
        return new MySQLSequence();
    }


    @Bean
    @Condition(onMissingBean = TemplateMapper.class)
    public TemplateMapper templateMapper(SessionTemplate sessionTemplate, Sequence sequence) {
        return new DefaultTemplateMapper(sessionTemplate, sequence);
    }

    @Bean
    @Condition(onMissingBean = FlexMapper.class)
    public FlexMapper flexMapper(SessionTemplate sessionTemplate, ToSQL toSQL) {
        return new DefaultFlexMapper(sessionTemplate, toSQL);
    }
}
