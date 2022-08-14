package com.moxa.dream.system.mapped;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.system.mapper.MethodInfo;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedStatement {

    private MappedSql mappedSql;
    private List<MappedParam> mappedParamList;
    private MethodInfo methodInfo;
    private CacheKey uniqueKey;
    private Object arg;
    private Class<? extends Collection> rowType;
    private Class<?> colType;
    private Map<String, Object> envMap;

    private MappedStatement() {

    }

    public Configuration getConfiguration() {
        return methodInfo.getConfiguration();
    }

    public Command getCommand() {
        return mappedSql.getCommand();
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public String getId() {
        return methodInfo.getId();
    }

    public PackageStatement getStatement() {
        return methodInfo.getStatement();
    }

    public Object getArg() {
        return arg;
    }

    public String getSql() {
        return mappedSql.getSql();
    }

    public CacheKey getUniqueKey() {
        return uniqueKey;
    }

    public Class<?> getColType() {
        return colType;
    }

    public void setColType(Class<?> colType) {
        this.colType = colType;
    }

    public Class<? extends Collection> getRowType() {
        return rowType;
    }

    public void setRowType(Class<? extends Collection> rowType) {
        this.rowType = rowType;
    }
    public String[] getColumnNames() {
        return methodInfo.getColumnNames();
    }
    public Method getMethod() {
        return methodInfo.getMethod();
    }

    public CacheKey getSqlKey() {
        return methodInfo.getSqlKey();
    }

    public Map<String, ScanInvoker.TableScanInfo> getTableScanInfoMap() {
        return mappedSql.getTableScanInfoMap();
    }

    public Integer getTimeOut() {
        return methodInfo.getTimeOut();
    }

    public <T> T get(Class<T> type) {
        return methodInfo.get(type);
    }

    public <T> void set(Class<T> type, T value) {
        methodInfo.set(type, value);
    }

    public List<MappedParam> getMappedParamList() {
        return mappedParamList;
    }

    public Action[] getInitActionList() {
        return methodInfo.getInitActionList();
    }

    public Action[] getLoopActionList() {
        return methodInfo.getLoopActionList();
    }

    public Action[] getDestroyActionList() {
        return methodInfo.getDestroyActionList();
    }

    public void put(String key, Object value) {
        if (envMap == null) {
            envMap = new HashMap<String, Object>();
        }
        envMap.put(key, value);
    }

    public Object get(String key) {
        if (envMap == null) {
            return null;
        } else {
            return envMap.get(key);
        }

    }

    public static class Builder {
        private final MappedStatement mappedStatement;

        public Builder() {
            mappedStatement = new MappedStatement();
        }

        public Builder methodInfo(MethodInfo methodInfo) {
            mappedStatement.methodInfo = methodInfo;
            mappedStatement.rowType = methodInfo.getRowType();
            mappedStatement.colType = methodInfo.getColType();
            return this;
        }

        public Builder mappedSql(MappedSql mappedSql) {
            mappedStatement.mappedSql = mappedSql;
            return this;
        }

        public Builder mappedParamList(List<MappedParam> mappedParamList) {
            mappedStatement.mappedParamList = mappedParamList;
            return this;
        }

        public Builder arg(Object arg) {
            mappedStatement.arg = arg;
            return this;
        }

        public Builder uniqueKey(CacheKey uniqueKey) {
            mappedStatement.uniqueKey = uniqueKey;
            return this;
        }

        public MappedStatement build() {
            return mappedStatement;
        }
    }
}
