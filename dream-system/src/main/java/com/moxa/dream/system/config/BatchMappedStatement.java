package com.moxa.dream.system.config;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class BatchMappedStatement extends MappedStatement implements Iterator<BatchMappedStatement> {
    List<?> argList;
    int batchSize;
    List<MappedStatement> mappedStatementList;
    int fromIndex;
    int toIndex;
    boolean hasNext = true;

    public BatchMappedStatement(MethodInfo methodInfo, List<?> argList, int batchSize) {
        if (batchSize <= 0) {
            batchSize = Integer.MAX_VALUE;
        }
        this.methodInfo = methodInfo;
        this.argList = argList;
        this.batchSize = batchSize;
        mappedStatementList = new ArrayList<>(argList.size());
    }

    public void compile(DialectFactory dialectFactory) {
        for (Object arg : argList) {
            Map<String, Object> argMap;
            if (arg instanceof Map) {
                argMap = (Map<String, Object>) arg;
            } else {
                argMap = new ObjectMap(arg);
            }
            try {
                mappedStatementList.add(dialectFactory.compile(methodInfo, argMap));
            } catch (Exception e) {
                throw new DreamRunTimeException("抽象树方法" + methodInfo.getId() + "翻译失败，" + e.getMessage(), e);
            }
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
        int total = mappedStatementList.size();
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
