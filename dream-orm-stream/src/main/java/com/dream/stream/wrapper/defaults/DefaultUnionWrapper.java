package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.UnionWrapper;

public class DefaultUnionWrapper<T> extends AbstractQueryWrapper<T> implements UnionWrapper<T, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultUnionWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
