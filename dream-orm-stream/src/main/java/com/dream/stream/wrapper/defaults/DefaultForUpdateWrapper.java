package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.ForUpdateWrapper;

public class DefaultForUpdateWrapper<T> extends AbstractQueryWrapper<T> implements ForUpdateWrapper<T, DefaultQueryWrapper<T>> {

    public DefaultForUpdateWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
