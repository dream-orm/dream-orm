package com.dream.system.config;

import java.util.*;
import java.util.stream.Collectors;

public class BatchMappedStatement extends MappedStatement implements Iterator<BatchMappedStatement> {
    protected List<MappedStatement> mappedStatementList;
    protected int batchSize = 1000;
    protected int fromIndex;
    protected int toIndex;

    public BatchMappedStatement(List<MappedStatement> mappedStatementList) {
        this.mappedStatementList = mappedStatementList;
        this.methodInfo = mappedStatementList.get(0).methodInfo;
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
        List<MappedParam> allMappedParamList = new ArrayList<>();
        for (MappedStatement mappedStatement : mappedStatementList) {
            List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
            if (mappedParamList != null && !mappedParamList.isEmpty()) {
                allMappedParamList.addAll(mappedParamList);
            }
        }
        return allMappedParamList;
    }

    public List<MappedStatement> getMappedStatementList() {
        return mappedStatementList.subList(fromIndex, toIndex);
    }

    public List<MappedStatement> getAllMappedStatementList() {
        return mappedStatementList;
    }

    public void setBatchSize(int batchSize) {
        if (batchSize <= 0) {
            batchSize = Integer.MAX_VALUE;
        }
        this.batchSize = batchSize;
    }
}
