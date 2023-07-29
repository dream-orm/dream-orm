package com.moxa.dream.solon.plugin;

import java.util.Map;
import java.util.Properties;

public class DreamProperties {

    private String toSQL;

    private String cache;

    private String myFunctionFactory;

    private String[] Interceptors;

    private String[] injects;

    private String[] invokers;

    private String[] typeHandlerWrappers;

    private String[] listeners;
    private Map<String, Properties> datasource;

    public String getToSQL() {
        return toSQL;
    }

    public void setToSQL(String toSQL) {
        this.toSQL = toSQL;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getMyFunctionFactory() {
        return myFunctionFactory;
    }

    public void setMyFunctionFactory(String myFunctionFactory) {
        this.myFunctionFactory = myFunctionFactory;
    }

    public String[] getInterceptors() {
        return Interceptors;
    }

    public void setInterceptors(String[] interceptors) {
        Interceptors = interceptors;
    }

    public String[] getInjects() {
        return injects;
    }

    public void setInjects(String[] injects) {
        this.injects = injects;
    }

    public String[] getInvokers() {
        return invokers;
    }

    public void setInvokers(String[] invokers) {
        this.invokers = invokers;
    }

    public String[] getTypeHandlerWrappers() {
        return typeHandlerWrappers;
    }

    public void setTypeHandlerWrappers(String[] typeHandlerWrappers) {
        this.typeHandlerWrappers = typeHandlerWrappers;
    }

    public String[] getListeners() {
        return listeners;
    }

    public void setListeners(String[] listeners) {
        this.listeners = listeners;
    }

    public Map<String, Properties> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Properties> datasource) {
        this.datasource = datasource;
    }
}
