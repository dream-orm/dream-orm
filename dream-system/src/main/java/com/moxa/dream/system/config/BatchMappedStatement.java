package com.moxa.dream.system.config;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.util.common.ObjectMap;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class BatchMappedStatement extends MappedStatement implements Iterator<BatchMappedStatement> {
    List<?> argList;
    int batchSize;
    List<MappedStatement> mappedStatementList;
    int fromIndex;
    int toIndex;
    int total;
    boolean hasNext = true;

    public BatchMappedStatement(MethodInfo methodInfo, List<?> argList) {
        this(methodInfo, argList, 1000);
    }

    public BatchMappedStatement(MethodInfo methodInfo, List<?> argList, int batchSize) {
        if (batchSize <= 0) {
            batchSize = Integer.MAX_VALUE;
        }
        this.methodInfo = methodInfo;
        this.argList = argList;
        this.batchSize = batchSize;
        mappedStatementList = new ArrayList<>(total = argList.size());
    }

    public void compile(DialectFactory dialectFactory) {
        for (Object arg : argList) {
            Map<String, Object> argMap;
            if (arg instanceof Map) {
                argMap = (Map<String, Object>) arg;
            } else {
                argMap = new ObjectMap(arg);
            }
            mappedStatementList.add(dialectFactory.compile(methodInfo, argMap));
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public BatchMappedStatement next() {
        fromIndex = toIndex;
        toIndex = fromIndex + batchSize;
        if (toIndex >= total) {
            toIndex = total;
            hasNext = false;
        } else {
            hasNext = true;
        }
        return this;
    }

    public List<MappedStatement> getMappedStatementList() {
        return mappedStatementList.subList(fromIndex, toIndex);
    }

    @Override
    public String getId() {
        return getMappedStatementList().get(0).getId();
    }

    @Override
    public Method getMethod() {
        return getMappedStatementList().get(0).getMethod();
    }

    @Override
    public CacheKey getMethodKey() {
        return getMappedStatementList().get(0).getMethodKey();
    }

    @Override
    public Command getCommand() {
        return Command.BATCH;
    }

    @Override
    public PackageStatement getStatement() {
        return getMappedStatementList().get(0).getStatement();
    }

    @Override
    public Class<? extends Collection> getRowType() {
        return List.class;
    }

    @Override
    public Class<?> getColType() {
        return Object.class;
    }

    @Override
    public Set<String> getTableSet() {
        return getMappedStatementList().get(0).getTableSet();
    }

    @Override
    public String[] getColumnNames() {
        return getMappedStatementList().get(0).getColumnNames();
    }

    @Override
    public String getSql() {
        return getMappedStatementList().get(0).getSql();
    }

    @Override
    public Object getArg() {
        return getMappedStatementList().stream().map(mappedStatement -> mappedStatement.getArg()).collect(Collectors.toList());
    }

    @Override
    public List<MappedParam> getMappedParamList() {
        return getMappedStatementList().stream().flatMap(mappedStatement -> mappedStatement.getMappedParamList().stream()).collect(Collectors.toList());
    }
}
