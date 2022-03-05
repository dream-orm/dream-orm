package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ToAssist {
    private Map<Class, Object> customObjMap;
    private Map<String, InvokerFactory> invokerFactoryMap;
    private Map<String, Invoker> sqlInvokerMap;

    public ToAssist(List<InvokerFactory> invokerFactoryList, Map<Class, Object> customObjMap) {
        setInvokerFactoryList(invokerFactoryList);
        setCustomObjMap(customObjMap);
    }

    public void setCustomObjMap(Map<Class, Object> customObjMap) {
        if (!ObjectUtil.isNull(customObjMap)) {
            this.customObjMap = customObjMap;
        }
    }

    public void setInvokerFactoryList(List<InvokerFactory> invokerFactoryList) {
        if (!ObjectUtil.isNull(invokerFactoryList)) {
            invokerFactoryMap = new HashMap<>();
            sqlInvokerMap = new HashMap<>();
            for (InvokerFactory invokerFactory : invokerFactoryList) {
                ObjectUtil.requireNonNull(invokerFactory, "Property  'sqlInvokerFactory' is required");
                ObjectUtil.requireTrue(!invokerFactoryMap.containsKey(invokerFactory.namespace()), "The namespace '" + invokerFactory.namespace() + "' already exists");
                invokerFactoryMap.put(invokerFactory.namespace(), invokerFactory);
            }
        }
    }


    public Statement beforeChain(Statement statement, ToSQL toSQL, Queue<Handler> handlerQueue, Handler[] handlerList, List<Invoker> invokerList) throws InvokerException {
        if (statement == null)
            return null;
        if (!ObjectUtil.isNull(handlerList))
            for (Handler handler : handlerList) {
                statement = handler.handlerBefore(statement, this, toSQL, handlerQueue, invokerList);
            }
        return statement;
    }

    public String afterChain(Queue<? extends Handler> handlerQueue, String sql) throws InvokerException {
        while (!handlerQueue.isEmpty())
            sql = handlerQueue.poll().handlerAfter(this, sql);
        return sql;
    }

    public Invoker getInvoker(String namespace, String function) {
        ObjectUtil.requireNonNull(function, "Property 'function' is required");
        function = function.toLowerCase();
        String invokerKey;
        Invoker invoker;
        if (namespace == null) {
            invokerKey = function;
            invoker = sqlInvokerMap.get(invokerKey);
            if (invoker == null) {
                for (InvokerFactory invokerFactory : invokerFactoryMap.values()) {
                    invoker = invokerFactory.create(function);
                    if (invoker != null) {
                        invokerKey = function + ":" + invokerFactory.namespace().toLowerCase();
                        sqlInvokerMap.put(function, invoker);
                        break;
                    }
                }
            } else
                return invoker;
        } else {
            invokerKey = function + ":" + namespace.toLowerCase();
            invoker = sqlInvokerMap.get(invokerKey);
            if (invoker == null) {
                InvokerFactory invokerFactory = invokerFactoryMap.get(namespace);
                ObjectUtil.requireNonNull(invokerFactory, namespace + " not known");
                invoker = invokerFactory.create(function);
                if (!sqlInvokerMap.containsKey(function)) {
                    sqlInvokerMap.put(function, invoker);
                }
            } else
                return invoker;
        }
        ObjectUtil.requireNonNull(invoker, invokerKey + " not known");
        sqlInvokerMap.put(invokerKey, invoker);
        invoker.init(this);
        return invoker;
    }

    public <T> T getCustom(Class<T> type) {
        ObjectUtil.requireNonNull(customObjMap, "customObjMap was not registered");
        return (T) customObjMap.get(type);
    }

    public Map<String, Invoker> getSqlInvokerMap() {
        return sqlInvokerMap;
    }
}
