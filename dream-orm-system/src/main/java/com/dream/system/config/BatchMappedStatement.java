package com.dream.system.config;

import com.dream.system.dialect.DialectFactory;
import com.dream.util.common.ObjectMap;
import com.dream.util.exception.DreamRunTimeException;

import java.util.*;
import java.util.stream.Collectors;

public class BatchMappedStatement extends MappedStatement implements Iterator<BatchMappedStatement> {
    List<?> argList;
    int batchSize = 1000;
    List<MappedStatement> mappedStatementList;
    int fromIndex;
    int toIndex;

    public BatchMappedStatement(MethodInfo methodInfo, List<?> argList) {
        this.methodInfo = methodInfo;
        this.cache = methodInfo.cache;
        this.argList = argList;
        mappedStatementList = compile(methodInfo.getConfiguration());
    }

    protected List<MappedStatement> compile(Configuration configuration) {
        DialectFactory dialectFactory = configuration.getDialectFactory();
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
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
        return mappedStatementList;
    }

    @Override
    public boolean hasNext() {
        return toIndex < mappedStatementList.size();
    }

    @Override
    public BatchMappedStatement next() {
        fromIndex = toIndex;
        toIndex = fromIndex + batchSize;
        int total = mappedStatementList.size();
        if (toIndex >= total) {
            toIndex = total;
        }
        return this;
    }

    public List<MappedStatement> getMappedStatementList() {
        return mappedStatementList.subList(fromIndex, toIndex);
    }

    public List<MappedStatement> getAllMappedStatementList() {
        return mappedStatementList;
    }

    @Override
    public Command getCommand() {
        return Command.BATCH;
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
        return mappedStatementList.get(0).getTableSet();
    }


    @Override
    public String getSql() {
        return mappedStatementList.get(0).getSql();
    }

    @Override
    public Object getArg() {
        return mappedStatementList.stream().map(mappedStatement -> mappedStatement.getArg()).collect(Collectors.toList());
    }

    @Override
    public List<MappedParam> getMappedParamList() {
        return mappedStatementList.stream().flatMap(mappedStatement -> mappedStatement.getMappedParamList().stream()).collect(Collectors.toList());
    }

    public void setBatchSize(int batchSize) {
        if (batchSize <= 0) {
            batchSize = Integer.MAX_VALUE;
        }
        this.batchSize = batchSize;
    }
}
