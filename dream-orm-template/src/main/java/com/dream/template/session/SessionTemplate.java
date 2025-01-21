package com.dream.template.session;

import com.dream.system.action.ActionProvider;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.core.session.SessionFactory;
import com.dream.system.mapper.MapperFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class SessionTemplate implements Session {
    private final Session sessionProxy;
    private Configuration configuration;
    private MapperFactory mapperFactory;
    private SessionHolder sessionHolder;

    public SessionTemplate(SessionHolder sessionHolder,
                           SessionFactory sessionFactory) {
        this.configuration = sessionFactory.getConfiguration();
        this.mapperFactory = configuration.getMapperFactory();
        this.sessionHolder = sessionHolder;
        this.sessionProxy = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{Session.class}, new SessionInvocationHandler());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperFactory.getMapper(type, this);
    }

    @Override
    public Object execute(ActionProvider actionProvider, Object arg) {
        return sessionProxy.execute(actionProvider, arg);
    }

    @Override
    public Object execute(MethodInfo methodInfo, Map<String, Object> argMap) {
        return sessionProxy.execute(methodInfo, argMap);
    }

    @Override
    public Object execute(MappedStatement mappedStatement) {
        return sessionProxy.execute(mappedStatement);
    }

    @Override
    public boolean isAutoCommit() {
        return false;
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public void close() {
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    private class SessionInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Session session = sessionHolder.getSession();
            try {
                Object result = method.invoke(session, args);
                if (!sessionHolder.isSessionTransactional(session)) {
                    session.commit();
                }
                return result;
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            } finally {
                if (session != null) {
                    sessionHolder.closeSession(session);
                }
            }
        }
    }
}
