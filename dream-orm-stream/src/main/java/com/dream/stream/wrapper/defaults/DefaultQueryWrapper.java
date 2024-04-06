package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.QueryWrapper;

public class DefaultQueryWrapper<T> extends AbstractQueryWrapper<T> implements QueryWrapper<T> {

    public DefaultQueryWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
