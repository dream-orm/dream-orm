package com.moxa.dream.module.mapped;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.EachInfo;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MappedStatement {

    private MappedSql mappedSql;
    private List<MappedParam> mappedParamList;
    private MethodInfo methodInfo;
    private CacheKey uniqueKey;
    private Object arg;
    private Class<? extends Collection> rowType;
    private Class<?> colType;
    private Map<String,Object>envMap;
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

    public List<EachInfo> getEachInfoList() {
        List<EachInfo> eachInfoList = methodInfo.getEachInfoList();
        return eachInfoList;
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
        private MappedStatement mappedStatement;

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

        public MappedStatement build() {
            mappedStatement.uniqueKey = mappedStatement.getSqlKey();
            if (!ObjectUtil.isNull(mappedStatement.mappedParamList)) {
                mappedStatement.uniqueKey.update(mappedStatement.mappedParamList.stream()
                        .map(mappedParam -> mappedParam.getParamValue())
                        .collect(Collectors.toList())
                        .toArray());
            }
            return mappedStatement;
        }
    }
}
