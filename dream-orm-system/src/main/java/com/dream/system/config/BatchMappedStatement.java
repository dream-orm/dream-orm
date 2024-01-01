package com.dream.system.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BatchMappedStatement<T extends BatchMappedStatement> extends MappedStatement implements Iterator<T> {
    protected List<MappedStatement> mappedStatementList;
    protected int batchSize = 1000;
    protected int fromIndex;
    protected int toIndex;

    public BatchMappedStatement(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
        this.cache = methodInfo.cache;
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
    public T next() {
        fromIndex = toIndex;
        toIndex = fromIndex + batchSize;
        int total = mappedStatementList.size();
        if (toIndex >= total) {
            toIndex = total;
        }
        return (T) this;
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
